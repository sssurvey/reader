package com.haomins.domain.repositories

import com.haomins.model.remote.user.UserAuthResponseModel
import io.reactivex.Single

interface LoginRepositoryContract {

    fun login(userName: String, userPassword: String): Single<UserAuthResponseModel>

    fun getSignUpUrlString(): String

    fun getForgetPasswordUrlString(): String

}