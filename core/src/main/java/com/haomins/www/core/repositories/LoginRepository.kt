package com.haomins.www.core.repositories

import com.haomins.www.core.data.models.user.UserAuthResponseModel
import com.haomins.www.core.policy.RxSchedulingPolicy
import com.haomins.www.core.service.TheOldReaderService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val defaultSchedulingPolicy: RxSchedulingPolicy
) {

    fun start(user: Pair<String, String>): Single<UserAuthResponseModel> {
        with(defaultSchedulingPolicy) {
            return theOldReaderService
                .loginUser(userEmail = user.first, userPassword = user.second)
                .defaultSchedulingPolicy()

        }
    }

    fun getSignUpUrlString(): String {
        return TheOldReaderService.SIGN_UP_PAGE_URL
    }

    fun getGenerateAccountUrlString(): String {
        return TheOldReaderService.GENERATE_ACCOUNT_PAGE_URL
    }

}