package com.haomins.reader.viewModels

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.haomins.data.util.getString
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    fun hasAuthToken(): Boolean {
        return sharedPreferences.getString(com.haomins.model.SharedPreferenceKey.AUTH_CODE_KEY).isNotEmpty()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}