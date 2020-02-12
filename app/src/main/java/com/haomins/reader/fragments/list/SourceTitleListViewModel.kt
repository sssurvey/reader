package com.haomins.reader.fragments.list

import android.util.Log
import androidx.lifecycle.ViewModel
import com.haomins.reader.models.subscription.SubscriptionResponse
import com.haomins.reader.networks.SourceSubscriptionListRequest
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class SourceTitleListViewModel @Inject constructor(
    private val sourceSubscriptionListRequest: SourceSubscriptionListRequest
) : ViewModel() {

    fun loadSourceSubscriptionList() {
        sourceSubscriptionListRequest.loadSubList().subscribe(
            object : DisposableSingleObserver<SubscriptionResponse>() {
                override fun onSuccess(t: SubscriptionResponse) {
                    Log.d("xxxx","${t}")
                }

                override fun onError(e: Throwable) {
                    Log.d("xxxx","${e.printStackTrace()}")
                }

            }
        )
    }

}