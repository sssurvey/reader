package com.haomins.reader.fragments.login

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.haomins.reader.models.user.User
import com.haomins.reader.models.user.UserAuthResponse
import com.haomins.reader.networks.LoginRequest
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRequest: LoginRequest,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    companion object {
        const val AUTH_CODE_KEY = "AUTH_CODE"
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.contains(AUTH_CODE_KEY)
    }

    @SuppressLint("CheckResult")
    fun login(user: User, doOnSuccess: () -> Unit, doOnError: () -> Unit) {

        //TODO: REMOVE logs, and check suppress lint issue
        
        loginRequest.start(user).subscribeWith(
            object :
                DisposableSingleObserver<UserAuthResponse>() {
                override fun onSuccess(t: UserAuthResponse) {
                    saveAuthCode(t.auth)
                    doOnSuccess()
                }

                override fun onError(e: Throwable) {
                    clearAuthCode()
                    doOnError()
                }
            })
    }

    private fun saveAuthCode(auth: String) {
        sharedPreferences.edit().putString(AUTH_CODE_KEY, auth).apply()
    }

    private fun clearAuthCode() {
        sharedPreferences.edit().remove(AUTH_CODE_KEY).apply()
    }
}