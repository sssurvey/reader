package com.haomins.reader.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.repositories.LoginRepository
import com.haomins.reader.utils.putValue
import com.haomins.reader.utils.removeValue
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
        const val AUTH_CODE_KEY = "AUTH_CODE"
    }

    val isUserLoggedIn by lazy { MutableLiveData(false) }
    private lateinit var disposable: Disposable

    fun authorize(user: Pair<String, String>) {
        disposable = loginRepository
            .start(user)
            .doOnSuccess { isUserLoggedIn.postValue(true) }
            .doOnError { isUserLoggedIn.postValue(false) }
            .subscribe({
                saveAuthCode(it.auth)
            },{
                clearAuthCode()
            })
    }

    private fun saveAuthCode(auth: String) {
        sharedPreferences.putValue(AUTH_CODE_KEY, auth)
    }

    private fun clearAuthCode() {
        sharedPreferences.removeValue(AUTH_CODE_KEY)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}