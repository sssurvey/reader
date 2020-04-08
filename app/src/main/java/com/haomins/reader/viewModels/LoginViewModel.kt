package com.haomins.reader.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.www.data.SharedPreferenceKey
import com.haomins.www.data.repositories.LoginRepository
import com.haomins.www.data.util.putValue
import com.haomins.www.data.util.removeValue
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
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
            }, {
                clearAuthCode()
            })
    }

    private fun saveAuthCode(auth: String) {
        sharedPreferences.putValue(SharedPreferenceKey.AUTH_CODE_KEY, auth)
    }

    private fun clearAuthCode() {
        sharedPreferences.removeValue(SharedPreferenceKey.AUTH_CODE_KEY)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}