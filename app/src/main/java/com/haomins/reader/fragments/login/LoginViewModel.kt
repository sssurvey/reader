package com.haomins.reader.fragments.login

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    fun isUserLoggedIn(): Boolean {
        return false
    }

    fun login(userName: String, password: String) {
        // call to do login with this
    }

}