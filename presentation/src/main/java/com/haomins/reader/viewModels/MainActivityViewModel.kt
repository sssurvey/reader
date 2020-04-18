package com.haomins.reader.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.haomins.www.core.data.SharedPreferenceKey
import com.haomins.www.core.util.getString
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun hasAuthToken(): Boolean {
        return sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY).isNotEmpty()
    }

}