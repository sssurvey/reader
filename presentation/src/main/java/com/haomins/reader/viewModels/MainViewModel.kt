package com.haomins.reader.viewModels

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.haomins.data.model.SharedPreferenceKey
import com.haomins.data.util.getString
import com.haomins.domain.model.entities.SubscriptionEntity
import com.haomins.domain.usecase.source.LoadSubscriptionList
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val loadSubscriptionList: LoadSubscriptionList
) : ViewModel() {

    fun hasAuthToken(): Boolean {
        return sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY).isNotEmpty()
    }

    fun loadSubscriptionList(doOnSuccess: () -> Unit = {}) {
        loadSubscriptionList.execute(
            object : DisposableSingleObserver<List<SubscriptionEntity>>() {
                override fun onSuccess(t: List<SubscriptionEntity>) {
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