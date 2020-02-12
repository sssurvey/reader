package com.haomins.reader.activities.main

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.R
import com.haomins.reader.fragments.list.SourceTitleListFragment
import com.haomins.reader.fragments.login.LoginFragment
import com.haomins.reader.fragments.login.LoginViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_main)
        handleLoginFragment()
    }

    override fun onBackPressed() {
        when (!sharedPreferences.getString(LoginViewModel.AUTH_CODE_KEY, "").isNullOrEmpty()) {
            true -> super.onBackPressed()
            false -> finish()
        }
    }

    fun showSourceTitleListFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.main_activity_frame_layout,
            SourceTitleListFragment(),
            SourceTitleListFragment.TAG
        ).commit()
    }

    private fun handleLoginFragment() {
        when (!sharedPreferences.getString(LoginViewModel.AUTH_CODE_KEY, "").isNullOrEmpty()) {
            true -> showSourceTitleListFragment()
            false -> showUserLoginFragment()
        }
    }

    private fun showUserLoginFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.main_activity_frame_layout,
            LoginFragment(),
            LoginFragment.TAG
        ).commit()
    }
}
