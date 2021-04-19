package com.haomins.reader.viewModels

import androidx.lifecycle.ViewModel
import com.haomins.reader.utils.DarkModeManager
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
        private val darkModeManager: DarkModeManager
) : ViewModel() {

    companion object {
        const val DARK_MODE_ENABLED_FLAG = 32
    }

    fun enableDarkMode() {
        darkModeManager.enableDarkMode()
    }

    fun disableDarkMode() {
        darkModeManager.disableDarkMode()
    }

    fun isDarkModeEnabled(): Boolean {
        return darkModeManager.checkIsCurrentDarkModeEnabled()
    }

}