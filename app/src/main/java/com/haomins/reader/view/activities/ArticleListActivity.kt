package com.haomins.reader.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.haomins.reader.R
import com.haomins.reader.utils.showToast
import com.haomins.reader.utils.slideInAnimation
import com.haomins.reader.utils.slideOutAnimation
import com.haomins.reader.view.fragments.ArticleDetailFragment
import com.haomins.reader.view.fragments.ArticleListFragment

class ArticleListActivity : AppCompatActivity() {

    companion object {
        const val SOURCE_FEED_ID = "SOURCE_FEED_ID"
        const val ARTICLE_ITEM_ID = "ARTICLE_ITEM_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slideInAnimation()
        overridePendingTransition(R.anim.start_slide_in_left, R.anim.start_slide_out_left)
        setContentView(R.layout.activity_article_list)
        showArticleListFragment()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideOutAnimation()
    }

    fun showArticleDetailsFragment(articleId: String) {
        showToast(articleId)
        val bundle = Bundle()
        val articleDetailFragment = ArticleDetailFragment()
        bundle.putString(ARTICLE_ITEM_ID, articleId)
        articleDetailFragment.arguments = bundle

        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.start_slide_in_left,
                R.anim.start_slide_out_left,
                R.anim.start_slide_in_right,
                R.anim.start_slide_out_right
            )
            add(
                R.id.article_list_activity_frame_layout,
                articleDetailFragment,
                ArticleDetailFragment.TAG
            )
            addToBackStack(null)
            commit()
        }
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