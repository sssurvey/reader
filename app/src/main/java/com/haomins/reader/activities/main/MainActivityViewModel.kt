package com.haomins.reader.activities.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.haomins.reader.fragments.login.LoginViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun hasAuthToken(): Boolean {
        return !sharedPreferences.getString(LoginViewModel.AUTH_CODE_KEY, "")
            .isNullOrEmpty()
    }

}