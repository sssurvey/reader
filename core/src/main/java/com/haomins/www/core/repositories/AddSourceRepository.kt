package com.haomins.www.core.repositories

import android.content.SharedPreferences
import com.haomins.www.core.data.SharedPreferenceKey
import com.haomins.www.core.data.models.subscription.AddSubscriptionResponseModel
import com.haomins.www.core.policy.RxSchedulingPolicy
import com.haomins.www.core.service.TheOldReaderService
import com.haomins.www.core.util.getString
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddSourceRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPreferences: SharedPreferences,
    private val rxSchedulingPolicy: RxSchedulingPolicy
) {

    fun addSource(source: String): Single<AddSubscriptionResponseModel> {
        with (rxSchedulingPolicy) {
            return theOldReaderService.addSubscription(
                headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                        + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY)),
                quickAddSubscription = source
            ).defaultSchedulingPolicy()
        }
    }

}