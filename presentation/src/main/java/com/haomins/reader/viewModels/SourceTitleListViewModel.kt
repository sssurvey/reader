package com.haomins.reader.viewModels

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.www.data.model.entities.SubscriptionEntity
import com.haomins.www.data.repositories.SourceSubscriptionListRepository
import com.haomins.www.data.service.TheOldReaderService
import io.reactivex.Observable
import java.net.URL
import javax.inject.Inject

class SourceTitleListViewModel @Inject constructor(
        private val sourceSubscriptionListRepository: SourceSubscriptionListRepository
) : ViewModel() {

    val sourceListUiDataSet by lazy {
        MutableLiveData(sourceUiDataList)
    }

    private val sourceUiDataList = mutableListOf<Pair<String, URL>>()

    private lateinit var sourceListData: List<SubscriptionEntity>

    fun getSubSourceId(position: Int): String {
        return sourceListData[position].id
    }

    fun loadImageIcon(imageView: ImageView, url: URL) {
        sourceSubscriptionListRepository.loadIconImage(imageView, url)
    }

    fun loadSourceSubscriptionList() {
        sourceSubscriptionListRepository
                .loadSubList()
                .flatMap {
                    sourceUiDataList.clear()
                    sourceSubscriptionListRepository.saveSubListToDB(it)
                }
                .flatMap { sourceSubscriptionListRepository.retrieveSubListFromDB() }
                .onErrorResumeNext { sourceSubscriptionListRepository.retrieveSubListFromDB() }
                .doOnSuccess { sourceListData = it }
                .toObservable()
                .flatMap { populateSubSourceDataSet(it) }
                .doAfterNext { sourceListUiDataSet.postValue(sourceUiDataList) }
                .subscribe()
    }

    private fun populateSubSourceDataSet(subscriptionEntities: List<SubscriptionEntity>) =
            Observable.fromIterable(subscriptionEntities).doOnNext {
                sourceUiDataList.add(
                        Pair(
                                first = it.title,
                                second = URL(TheOldReaderService.DEFAULT_PROTOCOL + it.iconUrl)
                        )
                )
            }
}