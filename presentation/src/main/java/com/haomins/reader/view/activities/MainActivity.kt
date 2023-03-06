package com.haomins.reader.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.haomins.reader.R
import com.haomins.reader.utils.delayedUiOperation
import com.haomins.reader.view.fragments.ArticleListFragment
import com.haomins.reader.view.fragments.ArticleListFragment.Companion.LOAD_MODE_KEY
import com.haomins.reader.view.fragments.DisclosureFragment
import com.haomins.reader.view.fragments.LoginFragment
import com.haomins.reader.view.fragments.SourceTitleListFragment
import com.haomins.reader.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showSplashArt()
        handleLoginFragment()
    }

    override fun onBackPressed() {
        when (mainViewModel.hasAuthToken()) {
            true -> super.onBackPressed()
            false -> this.handleOnBackPressed()
        }
    }

    fun startArticleListActivity(feedId: String) {
        Intent(this, ArticleListActivity::class.java).apply {
            putExtra(LOAD_MODE_KEY, ArticleListFragment.ArticleListViewMode.LOAD_BY_FEED_ID)
            putExtra(ArticleListFragment.ArticleListViewMode.LOAD_BY_FEED_ID.key, feedId)
        }.let {
            startActivity(it)
        }
    }

    fun startSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    fun startArticleListActivityForAllItems() {
        val intent = Intent(this, ArticleListActivity::class.java).apply {
            putExtra(LOAD_MODE_KEY, ArticleListFragment.ArticleListViewMode.LOAD_ALL)
        }
        startActivity(intent)
    }

    fun startAddSourceActivity() {
        startActivity(Intent(this, AddSourceActivity::class.java))
    }

    fun showDisclosureFragment() {
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(
                R.id.main_activity_frame_layout,
                DisclosureFragment(),
                DisclosureFragment.TAG
            )
            .addToBackStack(null)
            .commit()
    }

    fun showAfterUserLoggedInFragment() {

        fun showSourceListFragment() {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_activity_frame_layout,
                    SourceTitleListFragment(),
                    SourceTitleListFragment.TAG
                )
                .commit()
        }

        showSourceListFragment()
    }

    private fun showSplashArt() {
        delayedUiOperation(
            seconds = SPLASH_ART_COUNTDOWN_TIMER_SECONDS,
            doAfterDelay = { splash_screen.visibility = View.GONE }
        )
    }

    private fun handleOnBackPressed() {
        if (supportFragmentManager.backStackEntryCount >= 1) supportFragmentManager.popBackStack()
        else finish()
    }

    private fun handleLoginFragment() {
        when (mainViewModel.hasAuthToken()) {
            true -> showAfterUserLoggedInFragment()
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

    companion object {
        private const val SPLASH_ART_COUNTDOWN_TIMER_SECONDS = 2L
//        private const val TAG = "MainActivity"
    }
}
