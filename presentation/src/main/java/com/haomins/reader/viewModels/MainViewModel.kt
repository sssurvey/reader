package com.haomins.reader.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.haomins.data.model.SharedPreferenceKey
import com.haomins.data.util.getString
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun hasAuthToken(): Boolean {
        return sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY).isNotEmpty()
    }

}