package com.haomins.data.datastore.remote

import android.content.SharedPreferences
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.repositories.remote.SubscriptionRemoteRepository
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.subscription.SubscriptionItemModel
import io.reactivex.Single
import javax.inject.Inject

class SubscriptionRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPreferences: SharedPreferences
) : SubscriptionRemoteRepository {

    override fun loadSubscriptionList(): Single<List<SubscriptionItemModel>> {
        return theOldReaderService
            .loadSubscriptionSourceList(headerAuthValue = loadHeaderAuthValue())
            .map { it.subscriptions }
    }

    private fun loadHeaderAuthValue(): String {
        return (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY.string, ""))
    }

}