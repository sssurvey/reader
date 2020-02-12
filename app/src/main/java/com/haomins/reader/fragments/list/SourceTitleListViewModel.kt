package com.haomins.reader.fragments.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.reader.networks.SourceSubscriptionListRequest
import com.haomins.reader.networks.TheOldReaderService
import io.reactivex.observers.DisposableSingleObserver
import java.net.URL
import javax.inject.Inject

class SourceTitleListViewModel @Inject constructor(
    private val sourceSubscriptionListRequest: SourceSubscriptionListRequest
) : ViewModel() {

    val sourceListUiDataSet by lazy {
        MutableLiveData<List<Pair<String, URL>>>()
    }

    private lateinit var sourceListData: SubscriptionSourceListResponseModel

    fun loadSourceSubscriptionList() {
        sourceSubscriptionListRequest.loadSubList().subscribe(
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

    private fun refreshSourceListData(subscriptionSourceListResponseModel: SubscriptionSourceListResponseModel) {
        sourceListData = subscriptionSourceListResponseModel
    }

}