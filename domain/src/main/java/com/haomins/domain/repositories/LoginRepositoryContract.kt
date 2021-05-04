package com.haomins.domain.repositories

import io.reactivex.Single

interface LoginRepositoryContract {

    fun login(user: Pair<String, String>): Single<Any>

    fun getSignUpUrlString(): String

    fun getGenerateAccountUrlString(): String

}