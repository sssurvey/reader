package com.haomins.www.data.repositories

import com.haomins.www.data.model.responses.user.UserAuthResponseModel
import com.haomins.www.data.service.TheOldReaderService
import com.haomins.www.data.strategies.RxSchedulingStrategy
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
        private val theOldReaderService: TheOldReaderService,
        private val defaultSchedulingStrategy: RxSchedulingStrategy
) {

    fun start(user: Pair<String, String>): Single<UserAuthResponseModel> {
        with(defaultSchedulingStrategy) {
            return theOldReaderService
                    .loginUser(userEmail = user.first, userPassword = user.second)
                    .useDefaultSchedulingPolicy()

        }
    }

    fun getSignUpUrlString(): String {
        return TheOldReaderService.SIGN_UP_PAGE_URL
    }

    fun getGenerateAccountUrlString(): String {
        return TheOldReaderService.GENERATE_ACCOUNT_PAGE_URL
    }

}