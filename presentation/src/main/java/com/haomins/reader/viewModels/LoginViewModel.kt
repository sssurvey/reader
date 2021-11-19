package com.haomins.reader.viewModels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.domain.model.responses.UserAuthResponseModel
import com.haomins.domain.usecase.login.ForgetPassword
import com.haomins.domain.usecase.login.Login
import com.haomins.domain.usecase.login.SignUp
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val signUp: SignUp,
    private val login: Login,
    private val forgetPassword: ForgetPassword
) : ViewModel() {

    companion object {
        const val TAG = "LoginViewModel"
    }

    private val _isUserLoggedIn by lazy { MutableLiveData(false) }
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    fun authorize(userName: String, userPassword: String) {
        login.execute(
            observer = object : DisposableSingleObserver<UserAuthResponseModel>() {
                override fun onSuccess(t: UserAuthResponseModel) {
                    _isUserLoggedIn.postValue(true)
                }

                override fun onError(e: Throwable) {
                    _isUserLoggedIn.postValue(false)
                    Log.e(TAG, "userLogin :: onError -> ${e.printStackTrace()}")
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
                    Log.e(TAG, "forgetpassword :: onError -> ${e.printStackTrace()}")
                }
            }
        )
    }

}