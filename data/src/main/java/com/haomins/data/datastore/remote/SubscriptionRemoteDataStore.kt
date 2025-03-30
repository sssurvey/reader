package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.SharedPrefUtils
import com.haomins.domain.repositories.remote.SubscriptionRemoteRepository
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.subscription.SubscriptionItemModel
import io.reactivex.Single
import javax.inject.Inject

class SubscriptionRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPrefUtils: SharedPrefUtils,
) : SubscriptionRemoteRepository {

    override fun loadSubscriptionList(): Single<List<SubscriptionItemModel>> {
        return theOldReaderService
            .loadSubscriptionSourceList(headerAuthValue = loadHeaderAuthValue())
            .map { it.subscriptions }
    }

    private fun loadHeaderAuthValue(): String {
        return (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPrefUtils.getString(SharedPreferenceKey.AUTH_CODE_KEY))
    }

}