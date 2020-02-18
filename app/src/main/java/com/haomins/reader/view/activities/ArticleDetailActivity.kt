package com.haomins.reader.view.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.haomins.reader.R
import com.haomins.reader.view.fragments.ArticleDetailFragment

class ArticleDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_article_detail)
        showArticleDetailFragment()
    }

    private fun showArticleDetailFragment() {
        val bundle = Bundle()
        val articleDetailFragment = ArticleDetailFragment()
        bundle.putInt(
            ArticleListActivity.ARTICLE_ITEM_POSITION,
            intent.getIntExtra(ArticleListActivity.ARTICLE_ITEM_POSITION, -1)
        )
        bundle.putStringArrayList(
            ArticleListActivity.ARTICLE_ITEM_ID_LIST,
            intent.getStringArrayListExtra(ArticleListActivity.ARTICLE_ITEM_ID_LIST)
        )
        articleDetailFragment.arguments = bundle

        supportFragmentManager.beginTransaction().apply {
            replace(
                R.id.article_detail_activity_frame_layout,
                articleDetailFragment,
                ArticleDetailFragment.TAG
            )
            commit()
        }
    }

}