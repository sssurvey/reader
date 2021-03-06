package com.haomins.data.repositories

import android.content.SharedPreferences
import com.haomins.domain.model.responses.AddSourceResponseModel
import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.haomins.data.mapper.responsemapper.AddSourceResponseModelMapper
import com.haomins.data.model.SharedPreferenceKey
import com.haomins.data.service.TheOldReaderService
import com.haomins.data.util.getString
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
            .map {
                addSourceResponseModelMapper.dataModelToDomainModel(it)
            }
    }

}