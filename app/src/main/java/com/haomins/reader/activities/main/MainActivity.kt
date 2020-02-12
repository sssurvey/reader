package com.haomins.reader.activities.main

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.R
import com.haomins.reader.activities.articles.ArticleListActivity
import com.haomins.reader.fragments.list.SourceTitleListFragment
import com.haomins.reader.fragments.login.LoginFragment
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        mainActivityViewModel =
            ViewModelProviders.of(this, viewModelFactory)[MainActivityViewModel::class.java]
        setContentView(R.layout.activity_main)
        handleLoginFragment()
    }

    override fun onBackPressed() {
        when (mainActivityViewModel.hasAuthToken()) {
            true -> super.onBackPressed()
            false -> finish()
        }
    }

    fun startArticleListActivity(feedId: String) {
//        val options = ActivityOptions.makeSceneTransitionAnimation(this)
        val intent = Intent(this, ArticleListActivity::class.java)
        intent.putExtra("FEED_ID", feedId)
//        startActivity(intent, options.toBundle())
        startActivity(intent)
    }

    fun showSourceTitleListFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.main_activity_frame_layout,
            SourceTitleListFragment(),
            SourceTitleListFragment.TAG
        ).commit()
    }

    private fun handleLoginFragment() {
        when (mainActivityViewModel.hasAuthToken()) {
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
