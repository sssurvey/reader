package com.haomins.data.repositories.remote

import android.content.SharedPreferences
import com.haomins.data.service.TheOldReaderService
import com.haomins.data.util.putValue
import com.haomins.data.util.removeValue
import com.haomins.domain.repositories.LoginRemoteRepository
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.user.UserAuthResponseModel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPreferences: SharedPreferences,
) : LoginRemoteRepository {

    override fun login(userName: String, userPassword: String): Single<UserAuthResponseModel> {
        return theOldReaderService
            .loginUser(userName, userPassword)
            .doOnError {
                sharedPreferences.removeValue(SharedPreferenceKey.AUTH_CODE_KEY)
            }
            .map {
                sharedPreferences.putValue(SharedPreferenceKey.AUTH_CODE_KEY, it.auth)
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