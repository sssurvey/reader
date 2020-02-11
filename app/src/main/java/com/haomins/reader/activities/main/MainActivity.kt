package com.haomins.reader.activities.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.R
import com.haomins.reader.fragments.login.LoginFragment
import com.haomins.reader.fragments.login.LoginViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var loginViewModel: LoginViewModel

//    private val mainActivityViewModel by lazy {
//        ViewModelProvider(this).get(MainActivityViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]
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
            true -> showSubscriptionListFragment()
            false -> showUserLoginFragment()
        }
    }

    private fun showUserLoginFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.main_activity_frame_layout,
            LoginFragment()
        ).addToBackStack(LoginFragment.TAG).commit()
    }

    private fun showSubscriptionListFragment() {
        
    }
}
