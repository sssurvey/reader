package com.haomins.www.data.repositories

import android.content.SharedPreferences
import com.haomins.domain.model.AddSourceResponseModel
import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.haomins.www.data.mapper.AddSourceResponseModelMapper
import com.haomins.www.data.model.SharedPreferenceKey
import com.haomins.www.data.service.TheOldReaderService
import com.haomins.www.data.util.getString
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