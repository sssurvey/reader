package com.haomins.reader.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.haomins.reader.R
import com.haomins.reader.utils.slideInAnimation
import com.haomins.reader.utils.slideOutAnimation
import com.haomins.reader.view.fragments.ArticleListFragment
import com.haomins.reader.view.fragments.ArticleListFragment.Companion.LOAD_MODE_KEY

class ArticleListActivity : AppCompatActivity(), ArticleListFragment.HasClickableArticleList {

    companion object {
        const val ARTICLE_ITEM_POSITION = "ARTICLE_ITEM_POSITION"
        const val ARTICLE_ITEM_ID_ARRAY = "ARTICLE_ITEM_ID_ARRAY"

        private const val TAG = "ArticleListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "::onCreate")
        slideInAnimation()
        setContentView(R.layout.activity_article_list)
        checkIntent()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideOutAnimation()
    }

    override fun startArticleDetailActivity(position: Int, articleIdArray: Array<String>) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra(ARTICLE_ITEM_POSITION, position)
        intent.putExtra(ARTICLE_ITEM_ID_ARRAY, articleIdArray)
        startActivity(intent)
    }

    private fun checkIntent() {
        when (intent.getSerializableExtra(LOAD_MODE_KEY)) {
            ArticleListFragment.ArticleListViewMode.LOAD_BY_FEED_ID -> showArticleListFragment()
            ArticleListFragment.ArticleListViewMode.LOAD_ALL -> showArticleListFragmentForAllItems()
        }
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


}