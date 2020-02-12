package com.haomins.reader.fragments.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.reader.networks.SourceSubscriptionListRequest
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class SourceTitleListViewModel @Inject constructor(
    private val sourceSubscriptionListRequest: SourceSubscriptionListRequest
) : ViewModel() {

    val sourceListDataSet by lazy {
        MutableLiveData<List<Pair<String, String>>>()
    }

    fun loadSourceSubscriptionList() {
        sourceSubscriptionListRequest.loadSubList().subscribe(
            object : DisposableSingleObserver<SubscriptionSourceListResponseModel>() {
                override fun onSuccess(t: SubscriptionSourceListResponseModel) {
                    populatedSubSourceDataSet(subscriptionSourceListResponseModel = t)
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
        val sourceItemDisplayDataLists = ArrayList<Pair<String, String>>()
        subscriptionSourceListResponseModel.subscriptions.forEach {
            sourceItemDisplayDataLists.add(
                Pair(
                    first = it.title,
                    second = it.iconUrl
                )
            )
        }
        sourceListDataSet.postValue(sourceItemDisplayDataLists)
    }

}