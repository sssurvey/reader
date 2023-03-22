package com.haomins.data.repositories

import android.content.SharedPreferences
import com.haomins.data.mapper.responsemapper.AddSourceResponseModelMapper
import com.haomins.model.SharedPreferenceKey
import com.haomins.data.service.TheOldReaderService
import com.haomins.data.util.getString
import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.haomins.model.remote.subscription.AddSourceResponseModel
import io.reactivex.Single
import javax.inject.Inject

class AddSourceRepository @Inject constructor(
    private val addSourceResponseModelMapper: AddSourceResponseModelMapper,
    private val theOldReaderService: TheOldReaderService,
    private val sharedPreferences: SharedPreferences
) : AddSourceRepositoryContract {

    override fun addSource(source: String): Single<AddSourceResponseModel> {
        return theOldReaderService
            .addSubscription(
                headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                        + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY)),
                quickAddSubscription = source
            )
    }

}