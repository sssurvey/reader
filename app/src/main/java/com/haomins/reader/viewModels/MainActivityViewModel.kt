package com.haomins.reader.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.haomins.reader.utils.getValue
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun hasAuthToken(): Boolean {
        return sharedPreferences.getValue(LoginViewModel.AUTH_CODE_KEY).isNotEmpty()
    }

}