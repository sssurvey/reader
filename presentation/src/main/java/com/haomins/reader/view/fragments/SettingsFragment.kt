package com.haomins.reader.view.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.haomins.reader.R
import com.haomins.reader.viewModels.SettingsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        const val TAG = "SettingsFragment"
        const val DARK_MODE_ENABLED_FLAG = 32
        const val DARK_MODE_DISABLED_FLAG = 16
        private const val DARK_MODE_OPTION = "dark_mode"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var settingsViewModel: SettingsViewModel

    private val darkModeSwitchPreferenceCompat by lazy {
        findPreference<SwitchPreferenceCompat>(DARK_MODE_OPTION)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
        AndroidSupportInjection.inject(this)
        settingsViewModel =
            ViewModelProviders.of(this, viewModelFactory)[SettingsViewModel::class.java]
        configPreference()
        setOnclickListeners()
    }

    private fun configPreference() {
        when ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)) {
            DARK_MODE_ENABLED_FLAG -> darkModeSwitchPreferenceCompat?.isChecked = true
            DARK_MODE_DISABLED_FLAG -> darkModeSwitchPreferenceCompat?.isChecked = false
        }
    }

    private fun setOnclickListeners() {
        darkModeSwitchPreferenceCompat?.apply {
            isPersistent = true
            setOnPreferenceClickListener {
                when (isChecked) {
                    true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                true
            }
        }
    }
}