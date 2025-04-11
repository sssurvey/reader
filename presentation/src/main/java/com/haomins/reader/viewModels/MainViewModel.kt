package com.haomins.reader.viewModels

import androidx.lifecycle.ViewModel
import com.haomins.domain.common.PrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefUtils: PrefUtils,
) : ViewModel() {

    // TODO: [ISSUE-226] remove `runBlocking {}`
    fun hasAuthToken(): Boolean {
        return runBlocking {
            prefUtils
                .getString(com.haomins.model.PreferenceKey.AUTH_CODE_KEY)
                .isNotEmpty()
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}