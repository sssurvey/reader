package com.haomins.reader.view.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.haomins.reader.R
import com.haomins.reader.view.activities.SettingsActivity
import com.haomins.reader.viewModels.SettingsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        const val TAG = "SettingsFragment"
        private const val DARK_MODE_OPTION = "dark_mode"
        private const val PREFERENCE_ABOUT = "about"
        private const val PREFERENCE_FEEDBACK = "feedback"
        private const val FEED_BACK_EMAIL = "mailto:youngmobileachiever@gmail.com"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var settingsViewModel: SettingsViewModel

    private val darkModeSwitchPreferenceCompat by lazy {
        findPreference<SwitchPreferenceCompat>(DARK_MODE_OPTION)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
        settingsViewModel =
            ViewModelProviders.of(this, viewModelFactory)[SettingsViewModel::class.java]
        configPreference()
        setOnclickListeners()
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference?.key) {
            PREFERENCE_ABOUT -> (activity as? SettingsActivity)?.showAboutFragment()
            PREFERENCE_FEEDBACK -> showEmailApp()
            else -> Unit
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun showEmailApp() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(FEED_BACK_EMAIL)
            putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_feed_back_greet_subject))
            putExtra(Intent.EXTRA_TEXT, getString(R.string.email_feed_back_greet_body))
        }
        startActivity(Intent.createChooser(emailIntent, getString(R.string.email_client_choose)))
    }

    private fun configPreference() {
        darkModeSwitchPreferenceCompat?.isChecked = settingsViewModel.isDarkModeEnabled()
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