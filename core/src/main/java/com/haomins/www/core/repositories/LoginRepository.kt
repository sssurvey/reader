package com.haomins.www.core.repositories

import com.haomins.www.core.service.TheOldReaderService
import com.haomins.www.core.data.models.user.UserAuthResponseModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginRepository @Inject constructor(private val theOldReaderService: TheOldReaderService) {

    fun start(user: Pair<String, String>): Single<UserAuthResponseModel> {
        return theOldReaderService
            .loginUser(userEmail = user.first, userPassword = user.second)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}