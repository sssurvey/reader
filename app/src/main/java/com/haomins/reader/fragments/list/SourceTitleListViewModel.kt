package com.haomins.reader.fragments.list

import android.util.Log
import androidx.lifecycle.ViewModel
import com.haomins.reader.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.reader.networks.SourceSubscriptionListRequest
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class SourceTitleListViewModel @Inject constructor(
    private val sourceSubscriptionListRequest: SourceSubscriptionListRequest
) : ViewModel() {

    fun loadSourceSubscriptionList() {
        sourceSubscriptionListRequest.loadSubList().subscribe(
            object : DisposableSingleObserver<SubscriptionSourceListResponseModel>() {
                override fun onSuccess(t: SubscriptionSourceListResponseModel) {
                    Log.d("xxxx", "${t}")
                }

                override fun onError(e: Throwable) {
                    Log.d("xxxx", "${e.printStackTrace()}")
                }

            }
        )
    }

}