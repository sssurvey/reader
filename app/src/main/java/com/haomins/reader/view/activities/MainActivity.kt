package com.haomins.reader.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.R
import com.haomins.reader.view.activities.ArticleListActivity.Companion.SOURCE_FEED_ID
import com.haomins.reader.view.fragments.LoginFragment
import com.haomins.reader.view.fragments.SourceTitleListFragment
import com.haomins.reader.viewModels.MainActivityViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

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
        val intent = Intent(this, ArticleListActivity::class.java)
        intent.putExtra(SOURCE_FEED_ID, feedId)
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
