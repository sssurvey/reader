package com.haomins.reader.view.fragments.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.haomins.reader.R
import com.haomins.reader.view.activities.SettingsActivity
import com.haomins.reader.viewModels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        const val TAG = "SettingsFragment"
        private const val DARK_MODE_OPTION = "dark_mode"
        private const val PREFERENCE_ABOUT = "about"
        private const val PREFERENCE_FEEDBACK = "feedback"
        private const val PREFERENCE_DISCLOSURES = "disclosures"
        private const val PREFERENCE_CLEAR_CACHE = "clear_cache"
        private const val INTENT_EMAIL_TYPE = "message/rfc822"
    }

    private val settingsViewModel by viewModels<SettingsViewModel>()

    private val darkModeSwitchPreferenceCompat by lazy {
        findPreference<SwitchPreferenceCompat>(DARK_MODE_OPTION)
    }

    override fun onCreatePreferences(
        savedInstanceState: Bundle?,
        rootKey: String?
    ) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
        configPreference()
        setOnclickListeners()
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference?.key) {
            PREFERENCE_ABOUT -> showAboutFragment()
            PREFERENCE_DISCLOSURES -> showDisclosuresFragment()
            PREFERENCE_FEEDBACK -> showEmailApp()
            PREFERENCE_CLEAR_CACHE -> showClearCacheFragment()
            else -> Unit
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun showAboutFragment() {
        (activity as? SettingsActivity)?.showAboutFragment()
    }

    private fun showDisclosuresFragment() {
        (activity as? SettingsActivity)?.showDisclosureFragment()
    }

    private fun showClearCacheFragment() {
        (activity as? SettingsActivity)?.showClearCacheFragment()
    }

    private fun showEmailApp() {
        settingsViewModel.getLogFileThenDo { email, fileUri ->
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = INTENT_EMAIL_TYPE
                putExtra(Intent.EXTRA_EMAIL, email)
                putExtra(Intent.EXTRA_STREAM, fileUri)
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    getString(R.string.email_feed_back_greet_subject)
                )
                putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.email_feed_back_greet_body)
                )
            }
            startActivity(
                Intent.createChooser(
                    emailIntent,
                    getString(R.string.email_client_choose)
                )
            )
        }
    }

    private fun configPreference() {
        darkModeSwitchPreferenceCompat?.isChecked =
            settingsViewModel.isDarkModeEnabled()
    }

    private fun setOnclickListeners() {
        darkModeSwitchPreferenceCompat?.apply {
            isPersistent = true
            setOnPreferenceClickListener {
                when (isChecked) {
                    true -> settingsViewModel.enableDarkMode()
                    false -> settingsViewModel.disableDarkMode()
                }
                true
            }
        }
    }
}