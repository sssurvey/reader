package com.haomins.www.model.repositories

import android.content.SharedPreferences
import com.haomins.www.model.model.SharedPreferenceKey
import com.haomins.www.model.model.responses.subscription.AddSubscriptionResponseModel
import com.haomins.www.model.strategies.RxSchedulingStrategy
import com.haomins.www.model.service.TheOldReaderService
import com.haomins.www.model.util.getString
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddSourceRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPreferences: SharedPreferences,
    private val defaultSchedulingStrategy: RxSchedulingStrategy
) {

    fun addSource(source: String): Single<AddSubscriptionResponseModel> {
        with (defaultSchedulingStrategy) {
            return theOldReaderService.addSubscription(
                headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                        + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY)),
                quickAddSubscription = source
            ).useDefaultSchedulingPolicy()
        }
    }

}