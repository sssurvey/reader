package com.haomins.reader.networks

import com.haomins.reader.models.user.User
import com.haomins.reader.models.user.UserAuthResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginRequest @Inject constructor(private val theOldReaderService: TheOldReaderService) {

    fun start(user: User): Single<UserAuthResponse> {
        return theOldReaderService
            .loginUser(userEmail = user.userEmail, userPassword = user.userPassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}