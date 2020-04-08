package com.haomins.reader.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.TheOldReaderService
import com.haomins.www.data.db.entities.SubscriptionEntity
import com.haomins.www.data.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.reader.repositories.SourceSubscriptionListRepository
import io.reactivex.observers.DisposableSingleObserver
import java.net.URL
import javax.inject.Inject

class SourceTitleListViewModel @Inject constructor(
    private val sourceSubscriptionListRepository: SourceSubscriptionListRepository
) : ViewModel() {

    val sourceListUiDataSet by lazy {
        MutableLiveData<List<Pair<String, URL>>>()
    }

    private lateinit var sourceListData: List<SubscriptionEntity>

    fun getSubSourceId(position: Int): String {
        return sourceListData[position].id
    }

    fun loadSourceSubscriptionList() {
        sourceSubscriptionListRepository.loadSubList()
            .subscribe(object : DisposableSingleObserver<SubscriptionSourceListResponseModel>() {
                override fun onSuccess(t: SubscriptionSourceListResponseModel) {
                    sourceSubscriptionListRepository.saveSubListToDB(t)
                    loadSourceSubscriptionListFromDB()
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    loadSourceSubscriptionListFromDB()
                }
            })
    }

    private fun loadSourceSubscriptionListFromDB() {
        sourceSubscriptionListRepository.retrieveSubListFromDB()
            .subscribe(object : DisposableSingleObserver<List<SubscriptionEntity>>() {
                override fun onSuccess(t: List<SubscriptionEntity>) {
                    refreshSourceListData(t)
                    populatedSubSourceDataSet(t)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    private fun populatedSubSourceDataSet(subscriptionEntities: List<SubscriptionEntity>) {
        val sourceItemDisplayDataLists = ArrayList<Pair<String, URL>>()
        subscriptionEntities.forEach {
            sourceItemDisplayDataLists.add(
                Pair(
                    first = it.title,
                    second = URL(TheOldReaderService.DEFAULT_PROTOCOL + it.iconUrl)
                )
            )
        }
        sourceListUiDataSet.postValue(sourceItemDisplayDataLists)
    }

    private fun refreshSourceListData(subscriptionEntities: List<SubscriptionEntity>) {
        sourceListData = subscriptionEntities
    }

}