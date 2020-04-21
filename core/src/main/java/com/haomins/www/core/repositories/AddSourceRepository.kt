package com.haomins.www.core.repositories

import android.content.SharedPreferences
import com.haomins.www.core.data.SharedPreferenceKey
import com.haomins.www.core.data.models.subscription.AddSubscriptionResponseModel
import com.haomins.www.core.service.TheOldReaderService
import com.haomins.www.core.util.getString
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddSourceRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPreferences: SharedPreferences
) {

    fun addSource(source: String): Single<AddSubscriptionResponseModel> {
        return theOldReaderService.addSubscription(
            headerAuthValue = sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY),
            quickAddSubscription = source
        ).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread())
    }

}