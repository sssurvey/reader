package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.reader.repositories.SourceSubscriptionListRepository
import com.haomins.reader.TheOldReaderService
import io.reactivex.observers.DisposableSingleObserver
import java.net.URL
import javax.inject.Inject

class SourceTitleListViewModel @Inject constructor(
    private val sourceSubscriptionListRepository: SourceSubscriptionListRepository
) : ViewModel() {

    val sourceListUiDataSet by lazy {
        MutableLiveData<List<Pair<String, URL>>>()
    }

    private lateinit var sourceListData: SubscriptionSourceListResponseModel

    fun loadSourceSubscriptionList() {
        sourceSubscriptionListRepository.loadSubList().subscribe(
            object : DisposableSingleObserver<SubscriptionSourceListResponseModel>() {
                override fun onSuccess(t: SubscriptionSourceListResponseModel) {
                    populatedSubSourceDataSet(subscriptionSourceListResponseModel = t)
                    refreshSourceListData(t)
                }

                override fun onError(e: Throwable) {
                    Log.d("xxxx", "${e.printStackTrace()}")
                }

            }
        )
    }

    fun populatedSubSourceDataSet(
        subscriptionSourceListResponseModel: SubscriptionSourceListResponseModel
    ) {
        val sourceItemDisplayDataLists = ArrayList<Pair<String, URL>>()
        subscriptionSourceListResponseModel.subscriptions.forEach {
            sourceItemDisplayDataLists.add(
                Pair(
                    first = it.title,
                    second = URL(TheOldReaderService.DEFAULT_PROTOCOL + it.iconUrl)
                )
            )
        }
        sourceListUiDataSet.postValue(sourceItemDisplayDataLists)
    }

    fun getItemId(position: Int): String {
        return sourceListData.subscriptions[position].id
    }

    private fun refreshSourceListData(subscriptionSourceListResponseModel: SubscriptionSourceListResponseModel) {
        sourceListData = subscriptionSourceListResponseModel
    }

}