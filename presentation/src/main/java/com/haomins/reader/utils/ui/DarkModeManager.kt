package com.haomins.reader.utils.ui

import androidx.appcompat.app.AppCompatDelegate
import com.haomins.domain.common.PrefUtils
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DarkModeManager @Inject constructor(
    private val prefUtils: PrefUtils,
) {

    fun initialize() {
        when {
            followSystemDarkMode() ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

            checkIsCurrentDarkModeEnabled() ->
                enableDarkMode()

            else ->
                disableDarkMode()
        }
    }

    fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        saveDarkModeSettings(true)
    }

    fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        saveDarkModeSettings(false)
    }

    // TODO: [ISSUE-226] remove `runBlocking {}`
    fun checkIsCurrentDarkModeEnabled(): Boolean {
        return runBlocking {
            prefUtils.getBoolean(com.haomins.model.PreferenceKey.IS_DARK_MODE_ENABLED)
        }
    }

    // TODO: [ISSUE-226] remove `runBlocking {}`
    private fun followSystemDarkMode(): Boolean {
        return runBlocking {
            !prefUtils.containsBoolean(com.haomins.model.PreferenceKey.OVERRIDE_DARK_MODE_SETTINGS)
        }
    }

    // TODO: [ISSUE-226] remove `runBlocking {}`
    private fun saveDarkModeSettings(isEnabled: Boolean) {
        with(prefUtils) {
            runBlocking {
                putValue(
                    com.haomins.model.PreferenceKey.OVERRIDE_DARK_MODE_SETTINGS,
                    true
                )
                putValue(
                    com.haomins.model.PreferenceKey.IS_DARK_MODE_ENABLED,
                    isEnabled
                )
            }
        }
    }
}