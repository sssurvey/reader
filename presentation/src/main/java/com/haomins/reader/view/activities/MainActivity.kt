package com.haomins.reader.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.haomins.reader.BuildConfig
import com.haomins.reader.R
import com.haomins.reader.ReaderApplication
import com.haomins.reader.adapters.SourceTitleListAdapter
import com.haomins.reader.utils.delayedUiOperation
import com.haomins.reader.utils.showToast
import com.haomins.reader.view.fragments.ArticleListFragment
import com.haomins.reader.view.fragments.ArticleListFragment.Companion.LOAD_MODE_KEY
import com.haomins.reader.view.fragments.DisclosureFragment
import com.haomins.reader.view.fragments.LoginFragment
import com.haomins.reader.viewModels.MainViewModel
import com.haomins.reader.viewModels.SourceTitleListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar.view.*
import kotlinx.android.synthetic.main.fragment_source_list_title.*
import kotlinx.android.synthetic.main.layout_drawer.*
import kotlinx.android.synthetic.main.layout_drawer.view.*
import java.net.URL
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ArticleListFragment.HasClickableArticleList {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainViewModel by viewModels<MainViewModel> { viewModelFactory }
    private val sourceTitleListViewModel by viewModels<SourceTitleListViewModel> { viewModelFactory }
    private val sourceListDisplayDataList: MutableList<Pair<String, URL>> = mutableListOf()
    private val sourceTitleListAdapter by lazy {
        SourceTitleListAdapter(
            subSourceDisplayItems = sourceListDisplayDataList,
            sourceTitleListViewModel = sourceTitleListViewModel,
            onRowItemClicked = ::sourceListRecyclerViewItemClickedAt
        )
    }
    private val sourceListDataSetObserver by lazy {
        Observer<List<Pair<String, URL>>> {
            sourceListDisplayDataList.clear()
            sourceListDisplayDataList.addAll(it)
            sourceTitleListAdapter.notifyDataSetChanged()
        }
    }
    private val recyclerLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ReaderApplication).appComponent.viewModelComponent().build().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showSplashArt()
        handleLoginFragment()
        setOnClickListeners()
        registerLiveDataObserver()
    }

    override fun startArticleDetailActivity(position: Int, articleIdArray: Array<String>) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra(ArticleListActivity.ARTICLE_ITEM_POSITION, position)
        intent.putExtra(ArticleListActivity.ARTICLE_ITEM_ID_ARRAY, articleIdArray)
        startActivity(intent)
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

        fun showArticleListFragmentForAllItems() {
            val articleListFragment = ArticleListFragment().let {
                it.arguments = Bundle().apply {
                    putSerializable(LOAD_MODE_KEY, ArticleListFragment.ArticleListViewMode.LOAD_ALL)
                    putBoolean(
                        ArticleListFragment.ArticleListViewMode.LOAD_ALL.key,
                        intent.getBooleanExtra(
                            ArticleListFragment.ArticleListViewMode.LOAD_ALL.key,
                            true
                        )
                    )
                }
                it
            }
            supportFragmentManager.beginTransaction().replace(
                R.id.main_activity_frame_layout,
                articleListFragment,
                ArticleListFragment.TAG
            ).commit()
        }

        fun initDrawer() {

            // initialize tool bar
            appbar_layout.visibility = View.VISIBLE
            with(toolbar) {
                setSupportActionBar(this)
                initializeButton1(resources.getString(R.string.toolbar_my_feed_button_text))
                initializeButton2(resources.getString(R.string.toolbar_explore_feed_button_text))
            }
            supportActionBar?.setDisplayShowTitleEnabled(false)
            appbar_layout.toolbar.apply {
                setNavigationOnClickListener { openDrawer() }
                setNavigationIcon(R.drawable.ic_menu)
            }

            // set up drawer
            navigation_view.itemIconTintList = null
            navigation_view.drawer_login_app_version_text_view.text =
                getString(R.string.version_description, BuildConfig.VERSION_NAME)

            // load source list
            sourceTitleListViewModel.loadSourceSubscriptionList()
            source_list.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = sourceTitleListAdapter
            }

            unlockDrawer()
        }

        mainViewModel.loadSubscriptionList {
            showArticleListFragmentForAllItems()
        }
        initDrawer()
        initNavigationBar()
    }

    private fun registerLiveDataObserver() {
        sourceTitleListViewModel.apply {
            sourceListUiDataSet.observe(
                this@MainActivity,
                sourceListDataSetObserver
            )
        }
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

    private fun setOnClickListeners() {
        setNavigationViewOnItemClickListener()
    }

    //TODO: rework drawer, to have source list displayed in drawer
    private fun setNavigationViewOnItemClickListener() {
        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.option_all_articles -> startArticleListActivityForAllItems()
                R.id.option_settings -> startSettingsActivity()
                R.id.option_add_source -> startAddSourceActivity()
                else -> showToast("Still under development...")
            }
            closeDrawer()
            true
        }
    }

    private fun startArticleListActivityForAllItems() {
        val intent = Intent(this, ArticleListActivity::class.java).apply {
            putExtra(LOAD_MODE_KEY, ArticleListFragment.ArticleListViewMode.LOAD_ALL)
        }
        startActivity(intent)
    }

    private fun startSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun startAddSourceActivity() {
        startActivity(Intent(this, AddSourceActivity::class.java))
    }

    private fun initNavigationBar() {
        with(reader_navigation_bar) {
            setOnClicks(
                addSourceOnClick = {
                    startAddSourceActivity()
                    setCurrentlySelected(0)
                },
                allSourceOnClick = {
                    this@MainActivity.showToast("TODO: allSourceOnClick")
                    setCurrentlySelected(1)
                },
                searchSourceOnClick = {
                    this@MainActivity.showToast("TODO: searchSourceOnClick")
                    setCurrentlySelected(2)
                },
                savedSourceOnClick = {
                    this@MainActivity.showToast("TODO: savedSourceOnClick")
                    setCurrentlySelected(3)
                })
            visibility = View.VISIBLE
        }
    }

    private fun sourceListRecyclerViewItemClickedAt(position: Int) {
        this.startArticleListActivity(sourceTitleListViewModel.getSubSourceId(position))
    }

    private fun lockDrawer() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private fun unlockDrawer() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private fun openDrawer() {
        drawer_layout.openDrawer(GravityCompat.END)
    }

    private fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.END)
    }

    private fun handleLoginFragment() {
        lockDrawer()
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
