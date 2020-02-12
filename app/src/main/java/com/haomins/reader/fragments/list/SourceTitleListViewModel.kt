package com.haomins.reader.fragments.list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.fragments.list.SourceTitleListFragment.SubSourceDisplayItem
import com.haomins.reader.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.reader.networks.SourceSubscriptionListRequest
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class SourceTitleListViewModel @Inject constructor(
    private val sourceSubscriptionListRequest: SourceSubscriptionListRequest
) : ViewModel() {

    val isSubscriptionListLoaded by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun loadSourceSubscriptionList(doOnLoad: (List<SubSourceDisplayItem>) -> Unit) {
        sourceSubscriptionListRequest.loadSubList().subscribe(
            object : DisposableSingleObserver<SubscriptionSourceListResponseModel>() {
                override fun onSuccess(t: SubscriptionSourceListResponseModel) {
                    val subSourceDisplayItems = convertResponseForDisplay(t)
                    doOnLoad(subSourceDisplayItems)
                    isSubscriptionListLoaded.postValue(true)
                }

                override fun onError(e: Throwable) {
                    Log.d("xxxx", "${e.printStackTrace()}")
                }

            }
        )
    }

    fun convertResponseForDisplay(subscriptionSourceListResponseModel: SubscriptionSourceListResponseModel): List<SubSourceDisplayItem> {
        val subSourceDisplayItems = ArrayList<SubSourceDisplayItem>()
        subscriptionSourceListResponseModel.subscriptions.forEach {
            subSourceDisplayItems.add(
                SubSourceDisplayItem(
                    sourceTitle = it.title,
                    sourceIconUrl = it.iconUrl
                )
            )
        }
        return subSourceDisplayItems
    }

}