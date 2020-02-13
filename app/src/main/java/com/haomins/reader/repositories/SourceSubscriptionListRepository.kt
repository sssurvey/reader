package com.haomins.reader.repositories

import android.content.SharedPreferences
import com.haomins.reader.viewModels.LoginViewModel
import com.haomins.reader.models.subscription.SubscriptionSourceListResponseModel
import io.reactivex.Single
import javax.inject.Inject

class SourceSubscriptionListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPreferences: SharedPreferences
) {

    fun loadSubList(): Single<SubscriptionSourceListResponseModel> {
        return theOldReaderService.loadSubscriptionSourceList(
            headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                    + sharedPreferences.getString(LoginViewModel.AUTH_CODE_KEY, ""))
        )
    }
}