package com.haomins.reader.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.haomins.reader.R
import com.haomins.reader.utils.slideInAnimation
import com.haomins.reader.utils.slideOutAnimation
import com.haomins.reader.view.fragments.ArticleListFragment

class ArticleListActivity : AppCompatActivity() {

    companion object {
        const val MODE = "MODE"
        const val ARTICLE_ITEM_POSITION = "ARTICLE_ITEM_POSITION"
        const val ARTICLE_ITEM_ID_ARRAY = "ARTICLE_ITEM_ID_ARRAY"
    }

    enum class Mode(val key: String) {
        LOAD_BY_FEED_ID("LOAD_BY_FEED_ID"),
        LOAD_ALL("LOAD_ALL")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slideInAnimation()
        setContentView(R.layout.activity_article_list)
        checkIntent()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideOutAnimation()
    }

    fun startArticleDetailActivity(position: Int, articleIdArray: Array<String>) {
        val intent = Intent(this, ArticleDetailActivity::class.java)
        intent.putExtra(ARTICLE_ITEM_POSITION, position)
        intent.putExtra(ARTICLE_ITEM_ID_ARRAY, articleIdArray)
        startActivity(intent)
    }

    private fun checkIntent() {
        when (intent.getSerializableExtra(MODE)) {
            Mode.LOAD_BY_FEED_ID -> showArticleListFragment()
            Mode.LOAD_ALL -> showArticleListFragmentForAllItems()
        }
    }

    private fun showArticleListFragmentForAllItems() {
        val bundle = Bundle()
        val articleListFragment = ArticleListFragment()
        bundle.putSerializable(MODE, intent.getSerializableExtra(MODE))
        bundle.putBoolean(Mode.LOAD_ALL.key, intent.getBooleanExtra(Mode.LOAD_ALL.key, true))
        articleListFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.article_list_activity_frame_layout,
            articleListFragment, ArticleListFragment.TAG
        ).commit()
    }

    private fun showArticleListFragment() {
        val bundle = Bundle()
        val articleListFragment = ArticleListFragment()
        bundle.putSerializable(MODE, intent.getSerializableExtra(MODE))
        bundle.putString(Mode.LOAD_BY_FEED_ID.key, intent.getStringExtra(Mode.LOAD_BY_FEED_ID.key))
        articleListFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.article_list_activity_frame_layout,
            articleListFragment, ArticleListFragment.TAG
        ).commit()
    }
}