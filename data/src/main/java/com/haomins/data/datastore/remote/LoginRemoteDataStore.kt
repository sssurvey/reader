package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.SharedPrefUtils
import com.haomins.domain.repositories.remote.LoginRemoteRepository
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.user.UserAuthResponseModel
import io.reactivex.Single
import javax.inject.Inject

class LoginRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPrefUtils: SharedPrefUtils,
) : LoginRemoteRepository {

    override fun login(userName: String, userPassword: String): Single<UserAuthResponseModel> {
        return theOldReaderService
            .loginUser(userName, userPassword)
            .doOnError {
                sharedPrefUtils.removeValue(SharedPreferenceKey.AUTH_CODE_KEY)
            }
            .map {
                sharedPrefUtils.putValue(SharedPreferenceKey.AUTH_CODE_KEY, it.auth)
                it
            }
    }

    override fun getSignUpUrlString(): String {
        return TheOldReaderService.SIGN_UP_PAGE_URL
    }

    override fun getForgetPasswordUrlString(): String {
        return TheOldReaderService.FORGET_PASSWORD_PAGE_URL
    }

}