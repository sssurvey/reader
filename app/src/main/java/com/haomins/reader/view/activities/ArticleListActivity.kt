package com.haomins.reader.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.haomins.reader.R
import com.haomins.reader.utils.showToast
import com.haomins.reader.utils.slideInAnimation
import com.haomins.reader.utils.slideOutAnimation
import com.haomins.reader.view.fragments.ArticleListFragment

class ArticleListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slideInAnimation()
        overridePendingTransition(R.anim.start_slide_in_left, R.anim.start_slide_out_left)
        setContentView(R.layout.activity_article_list)
        showArticleListFragment()
        showToast(intent.getStringExtra("FEED_ID"))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideOutAnimation()
    }

    private fun showArticleListFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.article_list_activity_frame_layout,
            ArticleListFragment(), ArticleListFragment.TAG
        ).commit()
    }
}