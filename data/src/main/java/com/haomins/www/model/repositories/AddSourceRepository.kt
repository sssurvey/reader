package com.haomins.www.model.repositories

import android.content.SharedPreferences
import com.haomins.domain.model.AddSourceResponseModel
import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.haomins.www.model.mapper.AddSourceResponseModelMapper
import com.haomins.www.model.model.SharedPreferenceKey
import com.haomins.www.model.service.TheOldReaderService
import com.haomins.www.model.strategies.RxSchedulingStrategy
import com.haomins.www.model.util.getString
import io.reactivex.Single
import javax.inject.Inject

class AddSourceRepository @Inject constructor(
        private val addSourceResponseModelMapper: AddSourceResponseModelMapper,
        private val theOldReaderService: TheOldReaderService,
        private val sharedPreferences: SharedPreferences,
        private val defaultSchedulingStrategy: RxSchedulingStrategy
) : AddSourceRepositoryContract {

    override fun addSource(source: String): Single<AddSourceResponseModel> {
        with(defaultSchedulingStrategy) {
            return theOldReaderService.addSubscription(
                    headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                            + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY)),
                    quickAddSubscription = source
            )
                    .map { addSourceResponseModelMapper.dataModelToDomainModel(it) }
                    .useDefaultSchedulingPolicy()
        }
    }

}