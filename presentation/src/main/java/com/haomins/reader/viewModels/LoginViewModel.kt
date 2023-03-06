package com.haomins.reader.viewModels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.domain.model.responses.UserAuthResponseModel
import com.haomins.domain.usecase.login.ForgetPassword
import com.haomins.domain.usecase.login.Login
import com.haomins.domain.usecase.login.SignUp
import com.haomins.reader.R
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signUp: SignUp,
    private val login: Login,
    private val forgetPassword: ForgetPassword,
    private val application: Application
) : ViewModel() {

    companion object {
        const val TAG = "LoginViewModel"

        private const val LOGIN_INCORRECT_AUTH_STATUS_CODE = "403"
    }

    private val _isUserLoggedIn by lazy { MutableLiveData(false) }
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    fun authorize(userName: String, userPassword: String, onError: (errorMessage: String) -> Unit) {
        login.execute(
            observer = object : DisposableSingleObserver<UserAuthResponseModel>() {
                override fun onSuccess(t: UserAuthResponseModel) {
                    _isUserLoggedIn.postValue(true)
                }

                override fun onError(e: Throwable) {
                    _isUserLoggedIn.postValue(false)
                    Log.e(TAG, "userLogin :: onError -> ${e.printStackTrace()}")
                    onError(e.toUserReadableMessage())
                }
            },
            params = Login.forUserLogin(userName = userName, userPassword = userPassword)
        )
    }

    fun onSignUp(action: (Uri) -> Unit) {
        signUp.execute(
            observer = object : DisposableSingleObserver<String>() {
                override fun onSuccess(t: String) {
                    action.invoke(Uri.parse(t))
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "userSignUp :: onError -> ${e.printStackTrace()}")
                }
            }
        )
    }

    fun onForgetPassword(action: (Uri) -> Unit) {
        forgetPassword.execute(
            observer = object : DisposableSingleObserver<String>() {
                override fun onSuccess(t: String) {
                    action.invoke(Uri.parse(t))
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "forgetPassword :: onError -> ${e.printStackTrace()}")
                }
            }
        )
    }

    private fun Throwable.toUserReadableMessage(): String {
        message?.let {
            return when {
                it.contains(LOGIN_INCORRECT_AUTH_STATUS_CODE) ->
                    application.getString(R.string.warning_message_login_auth_failed)
                else -> it
            }
        }
        return application.getString(R.string.warning_message_unknown_exception)
    }

}