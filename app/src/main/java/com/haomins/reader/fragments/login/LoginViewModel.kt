package com.haomins.reader.fragments.login

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import com.haomins.reader.ReaderApplication
import com.haomins.reader.models.user.User
import com.haomins.reader.models.user.UserAuthResponse
import com.haomins.reader.networks.LoginRequest
import io.reactivex.observers.DisposableSingleObserver

class LoginViewModel : ViewModel() {

    fun isUserLoggedIn(): Boolean {
        return false
    }

    @SuppressLint("CheckResult")
    fun login(user: User, application: ReaderApplication, doOnSuccess: ()-> Unit) {

        //TODO: REMOVE logs, and check suppress lint issue
        
        LoginRequest(application, user).start().subscribeWith(
            object :
                DisposableSingleObserver<UserAuthResponse>() {
                override fun onSuccess(t: UserAuthResponse) {
                    Log.d("TEST", "login success ${t.auth}")
                    doOnSuccess()
                }

                override fun onError(e: Throwable) {
                    Log.d("TEST", "login failed ${e.printStackTrace()}")
                }
            })
    }
}