package com.haomins.domain.repositories

import com.haomins.domain.model.UserAuthResponseModel
import io.reactivex.Single

interface LoginRepositoryContract {

    fun login(user: Pair<String, String>): Single<UserAuthResponseModel>

    fun getSignUpUrlString(): String

    fun getGenerateAccountUrlString(): String

}