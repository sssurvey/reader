package com.haomins.reader.networks

import com.haomins.reader.ReaderApplication
import com.haomins.reader.models.user.User
import com.haomins.reader.models.user.UserAuthResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginRequest(
    readerApplication: ReaderApplication,
    private val user: User
) {

    private val retrofitInstance = readerApplication.getRetrofitClient()
    private val oldReaderApi = retrofitInstance.create(OldReaderApi::class.java)

    fun start(): Single<UserAuthResponse> {
        return oldReaderApi.loginUser(userEmail = user.userEmail, userPassword = user.userPassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}