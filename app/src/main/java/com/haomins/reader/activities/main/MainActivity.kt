package com.haomins.reader.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.haomins.reader.R
import com.haomins.reader.fragments.login.LoginFragment
import com.haomins.reader.fragments.login.LoginViewModel

class MainActivity : AppCompatActivity() {

    val loginViewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }

    private val mainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleLoginFragment()
    }

    override fun onBackPressed() {
        when (loginViewModel.isUserLoggedIn()) {
            true -> super.onBackPressed()
            false -> finish()
        }
    }

    private fun handleLoginFragment() {
        when (loginViewModel.isUserLoggedIn()) {
            true -> { /**/
            }
            false -> showUserLoginFragment()
        }
    }

    private fun showUserLoginFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.main_activity_frame_layout,
            LoginFragment()
        ).addToBackStack(LoginFragment.TAG).commit()
    }
}
