package com.haomins.reader.viewModels

import androidx.lifecycle.ViewModel
import com.haomins.domain.common.SharedPrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPrefUtils: SharedPrefUtils,
) : ViewModel() {

    fun hasAuthToken(): Boolean {
        return sharedPrefUtils
            .getString(com.haomins.model.SharedPreferenceKey.AUTH_CODE_KEY)
            .isNotEmpty()
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}