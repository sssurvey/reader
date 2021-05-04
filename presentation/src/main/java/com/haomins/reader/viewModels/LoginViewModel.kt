package com.haomins.reader.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.domain.model.UserAuthResponseModel
import com.haomins.domain.usecase.login.UserLogin
import com.haomins.domain.usecase.login.UserSignUp
import com.haomins.www.data.repositories.LoginRepository
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        private val loginRepository: LoginRepository,
        private val userSignUp: UserSignUp,
        private val userLogin: UserLogin
) : ViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _isUserLoggedIn by lazy { MutableLiveData(false) }
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    fun authorize(user: Pair<String, String>) {
        userLogin.execute(
                observer = object : DisposableSingleObserver<UserAuthResponseModel>() {
                    override fun onSuccess(t: UserAuthResponseModel) {
                        _isUserLoggedIn.postValue(true)
                    }

                    override fun onError(e: Throwable) {
                        _isUserLoggedIn.postValue(false)
                    }
                },
                params = UserLogin.forUserLogin(userName = user.first, userPassword = user.second)
        )
    }

    fun onSignUp(action: (Uri) -> Unit) {
        userSignUp.execute(
                observer = object : DisposableSingleObserver<String>() {
                    override fun onSuccess(t: String) {
                        action.invoke(Uri.parse(t))
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onSignUp :: onError -> ${e.printStackTrace()}")
                    }
                }
        )
    }

    fun getGenerateAccountForGoogleOrFacebookUrl(): Uri {
        return Uri.parse(loginRepository.getGenerateAccountUrlString())
    }

    override fun onCleared() {
        super.onCleared()
        userLogin.dispose()
        userSignUp.dispose()
    }
}