package com.haomins.reader.networks

import android.content.SharedPreferences
import com.haomins.reader.fragments.login.LoginViewModel
import com.haomins.reader.models.subscription.SubscriptionSourceListResponseModel
import io.reactivex.Single
import javax.inject.Inject

class SourceSubscriptionListRequest @Inject constructor(
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