package com.haomins.reader.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.haomins.reader.R
import com.haomins.reader.databinding.ActivityArticleListBinding
import com.haomins.reader.utils.slideInAnimation
import com.haomins.reader.utils.slideOutAnimation
import com.haomins.reader.view.fragments.articles.ArticleListFragment
import com.haomins.reader.view.fragments.articles.ArticleListFragment.Companion.LOAD_MODE_KEY
import com.haomins.reader.view.fragments.articles.LoadAllArticleListFragment
import com.haomins.reader.view.fragments.articles.LoadArticleListFragment
import com.haomins.reader.view.fragments.articles.LoadArticleListFragment.Companion.LOAD_BY_FEED_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListActivity : AppCompatActivity(), ArticleListFragment.HasClickableArticleList {

    companion object {
        const val ARTICLE_ITEM_ID = "ARTICLE_ITEM_ID"
        const val ARTICLE_ITEM_ID_ARRAY = "ARTICLE_ITEM_ID_ARRAY"

        private const val TAG = "ArticleListActivity"
    }

    private lateinit var binding: ActivityArticleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "::onCreate")
        inflateView()
        slideInAnimation()
        checkIntent()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideOutAnimation()
    }

    override fun startArticleDetailActivity(
        articleItemId: String,
        articleItemIdArray: Array<String>
    ) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra(ARTICLE_ITEM_ID, articleItemId)
        intent.putExtra(ARTICLE_ITEM_ID_ARRAY, articleItemIdArray)
        startActivity(intent)
    }

    //TODO: 143 double check later
    private fun checkIntent() {
        when (intent.getSerializableExtra(LOAD_MODE_KEY)) {
//            ArticleListFragment.ArticleListViewMode.LOAD_BY_FEED_ID -> showArticleListFragment()
//            ArticleListFragment.ArticleListViewMode.LOAD_ALL -> showArticleListFragmentForAllItems()
            ArticleListFragment.ArticleListViewMode.LOAD_BY_FEED_ID -> testFeed()
            ArticleListFragment.ArticleListViewMode.LOAD_ALL -> test()
        }
    }

    //TODO: 143 double check later
    private fun test() {
        Log.d(TAG, "::showArticleListFragmentForAllItems")
        val bundle = Bundle()
        val loadAllArticleListFragment = LoadAllArticleListFragment()
        loadAllArticleListFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.article_list_activity_frame_layout,
            loadAllArticleListFragment, LoadAllArticleListFragment.TAG
        ).commit()
    }

    //TODO: 143 double check later
    private fun testFeed() {
        Log.d(TAG, "::showArticleListFragment")
        val bundle = Bundle()
        val loadArticleListFragment = LoadArticleListFragment()
        bundle.putString(
            LOAD_BY_FEED_ID,
            intent.getStringExtra(ArticleListFragment.ArticleListViewMode.LOAD_BY_FEED_ID.key)
        )
        loadArticleListFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.article_list_activity_frame_layout,
            loadArticleListFragment, ArticleListFragment.TAG
        ).commit()
    }

    private fun showArticleListFragmentForAllItems() {
        Log.d(TAG, "::showArticleListFragmentForAllItems")
        val bundle = Bundle()
        val articleListFragment = ArticleListFragment()
        bundle.putSerializable(LOAD_MODE_KEY, intent.getSerializableExtra(LOAD_MODE_KEY))
        bundle.putBoolean(
            ArticleListFragment.ArticleListViewMode.LOAD_ALL.key,
            intent.getBooleanExtra(ArticleListFragment.ArticleListViewMode.LOAD_ALL.key, true)
        )
        articleListFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.article_list_activity_frame_layout,
            articleListFragment, ArticleListFragment.TAG
        ).commit()
    }

    private fun showArticleListFragment() {
        Log.d(TAG, "::showArticleListFragment")
        val bundle = Bundle()
        val articleListFragment = ArticleListFragment()
        bundle.putSerializable(LOAD_MODE_KEY, intent.getSerializableExtra(LOAD_MODE_KEY))
        bundle.putString(
            ArticleListFragment.ArticleListViewMode.LOAD_BY_FEED_ID.key,
            intent.getStringExtra(ArticleListFragment.ArticleListViewMode.LOAD_BY_FEED_ID.key)
        )
        articleListFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.article_list_activity_frame_layout,
            articleListFragment, ArticleListFragment.TAG
        ).commit()
    }

    private fun inflateView() {
        binding = ActivityArticleListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}