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
        const val SOURCE_FEED_ID = "SOURCE_FEED_ID"
        const val ARTICLE_ITEM_POSITION = "ARTICLE_ITEM_POSITION"
        const val ARTICLE_ITEM_ID_ARRAY = "ARTICLE_ITEM_ID_ARRAY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slideInAnimation()
        setContentView(R.layout.activity_article_list)
        showArticleListFragment()
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

    private fun showArticleListFragment() {
        val bundle = Bundle()
        val articleListFragment = ArticleListFragment()
        bundle.putString(SOURCE_FEED_ID, intent.getStringExtra(SOURCE_FEED_ID))
        articleListFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.article_list_activity_frame_layout,
            articleListFragment, ArticleListFragment.TAG
        ).commit()
    }
}