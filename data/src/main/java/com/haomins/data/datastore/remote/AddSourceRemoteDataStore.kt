package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.PrefUtils
import com.haomins.domain.repositories.remote.AddSourceRemoteRepository
import com.haomins.model.PreferenceKey
import com.haomins.model.remote.subscription.AddSourceResponseModel
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AddSourceRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val prefUtils: PrefUtils,
) : AddSourceRemoteRepository {

    override fun addSource(source: String): Single<AddSourceResponseModel> {
        return theOldReaderService
            .addSubscription(
                headerAuthValue = getHeaderAuthValue(),
                quickAddSubscription = source
            )
    }

    // TODO: [ISSUE-226] remove `runBlocking {}`
    private fun getHeaderAuthValue() = runBlocking {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + prefUtils.getString(PreferenceKey.AUTH_CODE_KEY))
    }
}