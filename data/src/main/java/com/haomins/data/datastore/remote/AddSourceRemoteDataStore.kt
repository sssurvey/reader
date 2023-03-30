package com.haomins.data.datastore.remote

import android.content.SharedPreferences
import com.haomins.data.service.TheOldReaderService
import com.haomins.data.util.getString
import com.haomins.domain.repositories.remote.AddSourceRemoteRepository
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.subscription.AddSourceResponseModel
import io.reactivex.Single
import javax.inject.Inject

class AddSourceRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPreferences: SharedPreferences
) : AddSourceRemoteRepository {

    override fun addSource(source: String): Single<AddSourceResponseModel> {
        return theOldReaderService
            .addSubscription(
                headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                        + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY)),
                quickAddSubscription = source
            )
    }

}