package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.PrefUtils
import com.haomins.domain.repositories.remote.SubscriptionRemoteRepository
import com.haomins.model.PreferenceKey
import com.haomins.model.remote.subscription.SubscriptionItemModel
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class SubscriptionRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val prefUtils: PrefUtils,
) : SubscriptionRemoteRepository {

    override fun loadSubscriptionList(): Single<List<SubscriptionItemModel>> {
        return theOldReaderService
            .loadSubscriptionSourceList(headerAuthValue = getHeaderAuthValue())
            .map { it.subscriptions }
    }

    // TODO: [ISSUE-226] remove `runBlocking {}`
    private fun getHeaderAuthValue() = runBlocking {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + prefUtils.getString(PreferenceKey.AUTH_CODE_KEY))
    }

}