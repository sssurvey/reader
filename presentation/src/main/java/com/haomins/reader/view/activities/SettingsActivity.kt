package com.haomins.reader.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.haomins.reader.R
import com.haomins.reader.databinding.ActivitySettingsBinding
import com.haomins.reader.utils.slideAnimation
import com.haomins.reader.utils.slideInAnimation
import com.haomins.reader.utils.slideOutAnimation
import com.haomins.reader.view.fragments.settings.AboutFragment
import com.haomins.reader.view.fragments.settings.ClearCacheFragment
import com.haomins.reader.view.fragments.settings.DisclosureFragment
import com.haomins.reader.view.fragments.settings.SettingsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateView()
        slideInAnimation()
        showSettingsFragment()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideOutAnimation()
    }

    fun showAboutFragment() {
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .slideAnimation()
            .replace(
                R.id.settings_container,
                AboutFragment(),
                AboutFragment.TAG
            )
            .addToBackStack(null)
            .commit()
    }

    fun showDisclosureFragment() {
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .slideAnimation()
            .replace(
                R.id.settings_container,
                DisclosureFragment(),
                DisclosureFragment.TAG
            )
            .addToBackStack(null)
            .commit()
    }

    fun showClearCacheFragment() {
        supportFragmentManager.commit {
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            slideAnimation()
            replace(
                R.id.settings_container,
                ClearCacheFragment(),
                ClearCacheFragment.TAG
            )
            addToBackStack(null)
        }
    }

    private fun showSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(
                R.id.settings_container,
                SettingsFragment(),
                SettingsFragment.TAG
            )
            .commit()
    }

    private fun inflateView() {
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}