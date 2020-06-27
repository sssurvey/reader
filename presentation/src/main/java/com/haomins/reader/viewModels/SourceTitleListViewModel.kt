package com.haomins.reader.viewModels

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.www.core.data.entities.SubscriptionEntity
import com.haomins.www.core.data.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.www.core.repositories.SourceSubscriptionListRepository
import com.haomins.www.core.service.TheOldReaderService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
            .flatMap { onLoadSuccess(it) }
            .onErrorResumeNext { loadSubListFromDB() }
            .doOnSuccess { refreshSourceListData(it) }
            .toObservable()
            .flatMap { populatedSubSourceDataSet(it) }
            .doAfterNext { updateSourceListUiDataSet() }
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun onLoadSuccess(subscriptionSourceListResponseModel: SubscriptionSourceListResponseModel)
            : Single<List<SubscriptionEntity>> {
        sourceUiDataList.clear()
        sourceSubscriptionListRepository.saveSubListToDB(subscriptionSourceListResponseModel)
        return loadSubListFromDB()
    }

    private fun loadSubListFromDB(): Single<List<SubscriptionEntity>> {
        return sourceSubscriptionListRepository.retrieveSubListFromDB()
    }

    private fun updateSourceListUiDataSet() {
        sourceListUiDataSet.postValue(sourceUiDataList)
    }

    private fun populatedSubSourceDataSet(subscriptionEntities: List<SubscriptionEntity>): Observable<SubscriptionEntity> {
        return Observable.fromIterable(subscriptionEntities).doOnNext {
            sourceUiDataList.add(it.toUiData())
        }
    }

    private fun SubscriptionEntity.toUiData(): Pair<String, URL> {
        return Pair(
            first = title,
            second = URL(TheOldReaderService.DEFAULT_PROTOCOL + iconUrl)
        )
    }

    private fun refreshSourceListData(subscriptionEntities: List<SubscriptionEntity>) {
        sourceListData = subscriptionEntities
    }
}