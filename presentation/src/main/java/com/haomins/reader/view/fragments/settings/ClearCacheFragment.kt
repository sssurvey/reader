package com.haomins.reader.view.fragments.settings

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.haomins.reader.R
import com.haomins.reader.viewModels.ClearCacheViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClearCacheFragment: PreferenceFragmentCompat() {

    private val clearCacheViewModel by viewModels<ClearCacheViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_clear_cache, rootKey)
        configPreferences()
    }

    private fun configPreferences() {
        refreshLocalDataSize()
        findPreference<Preference>(CLEAR_CACHE)?.setOnPreferenceClickListener {
            clearCacheViewModel.clearLocalData { refreshLocalDataSize() }
            true
        }
    }

    private fun refreshLocalDataSize() {
        clearCacheViewModel.getLocalDataSize { findPreference<Preference>(CACHE_SIZE)?.summary = "$it $DATA_UNIT" }
    }

    companion object {
        const val TAG = "ClearCacheFragment"
        private const val CACHE_SIZE = "cache_size"
        private const val CLEAR_CACHE = "clear_cache"
        private const val DATA_UNIT = "MB"
    }
}