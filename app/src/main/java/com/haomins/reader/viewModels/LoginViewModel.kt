package com.haomins.reader.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.models.user.UserAuthResponseModel
import com.haomins.reader.repositories.LoginRepository
import com.haomins.reader.utils.putValue
import com.haomins.reader.utils.removeValue
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
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
        loginRepository.start(user).subscribe(
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
        sharedPreferences.putValue(AUTH_CODE_KEY, auth)
    }

    private fun clearAuthCode() {
        sharedPreferences.removeValue(AUTH_CODE_KEY)
    }
}