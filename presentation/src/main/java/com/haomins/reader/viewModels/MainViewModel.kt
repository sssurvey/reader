package com.haomins.reader.viewModels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.haomins.data.util.getString
import com.haomins.domain_model.entities.SubscriptionEntity
import com.haomins.domain.usecase.source.LoadSubscriptionList
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val loadSubscriptionList: LoadSubscriptionList
) : ViewModel() {

    fun hasAuthToken(): Boolean {
        return sharedPreferences.getString(com.haomins.data_model.SharedPreferenceKey.AUTH_CODE_KEY).isNotEmpty()
    }

    fun loadSubscriptionList(doOnSuccess: () -> Unit = {}) {
        loadSubscriptionList.execute(
            object : DisposableSingleObserver<List<com.haomins.domain_model.entities.SubscriptionEntity>>() {
                override fun onSuccess(t: List<com.haomins.domain_model.entities.SubscriptionEntity>) {
                    Log.d(TAG, "loadSubscriptionList :: onSuccess loaded ${t.size} sources.")
                    doOnSuccess.invoke()
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "loadSubscriptionList :: onError")
                }
            }
        )
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}