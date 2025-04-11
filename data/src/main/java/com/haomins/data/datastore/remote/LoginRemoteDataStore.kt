package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.PrefUtils
import com.haomins.domain.repositories.remote.LoginRemoteRepository
import com.haomins.model.PreferenceKey
import com.haomins.model.remote.user.UserAuthResponseModel
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class LoginRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val prefUtils: PrefUtils,
) : LoginRemoteRepository {

    override fun login(
        userName: String,
        userPassword: String
    ): Single<UserAuthResponseModel> {
        return theOldReaderService
            .loginUser(userName, userPassword)
            .doOnError {
                // TODO: [ISSUE-226] remove `runBlocking {}`
                runBlocking {
                    prefUtils.removeStringValue(PreferenceKey.AUTH_CODE_KEY)
                }
            }
            .map {
                // TODO: [ISSUE-226] remove `runBlocking {}`
                runBlocking {
                    prefUtils.putValue(
                        PreferenceKey.AUTH_CODE_KEY,
                        it.auth
                    )
                    it
                }
            }
    }

    override fun getSignUpUrlString(): String {
        return TheOldReaderService.SIGN_UP_PAGE_URL
    }

    override fun getForgetPasswordUrlString(): String {
        return TheOldReaderService.FORGET_PASSWORD_PAGE_URL
    }

}