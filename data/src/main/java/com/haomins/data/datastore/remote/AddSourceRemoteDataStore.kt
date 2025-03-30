package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.SharedPrefUtils
import com.haomins.domain.repositories.remote.AddSourceRemoteRepository
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.subscription.AddSourceResponseModel
import io.reactivex.Single
import javax.inject.Inject

class AddSourceRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPrefUtils: SharedPrefUtils,
) : AddSourceRemoteRepository {

    override fun addSource(source: String): Single<AddSourceResponseModel> {
        return theOldReaderService
            .addSubscription(
                headerAuthValue = (
                        TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                                + sharedPrefUtils
                            .getString(SharedPreferenceKey.AUTH_CODE_KEY)
                        ),
                quickAddSubscription = source
            )
    }

}