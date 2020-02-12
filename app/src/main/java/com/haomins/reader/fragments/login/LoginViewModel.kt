package com.haomins.reader.fragments.login

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.models.user.UserAuthResponseModel
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

    val isUserLoggedIn by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun hasAuthKey(): Boolean {
        return sharedPreferences.contains(AUTH_CODE_KEY)
    }

    fun authorize(user: Pair<String, String>) {
        loginRequest.start(user).subscribe(
            object :
                DisposableSingleObserver<UserAuthResponseModel>() {
                override fun onSuccess(t: UserAuthResponseModel) {
                    saveAuthCode(t.auth)
                    isUserLoggedIn.postValue(true)
                }

                override fun onError(e: Throwable) {
                    clearAuthCode()
                    isUserLoggedIn.postValue(false)
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