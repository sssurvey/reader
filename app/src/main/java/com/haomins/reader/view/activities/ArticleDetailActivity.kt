package com.haomins.reader.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.haomins.reader.R
import com.haomins.reader.utils.slideInAnimation
import com.haomins.reader.utils.slideOutAnimation
import com.haomins.reader.view.fragments.ArticleDetailFragment
import kotlinx.android.synthetic.main.activity_article_detail.*

class ArticleDetailActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ITEM_ID = "ARTICLE_ITEM_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        slideInAnimation()
        setContentView(R.layout.activity_article_detail)
        initViewPager()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideOutAnimation()
    }

    private fun initViewPager() {
        val currentPosition = intent.getIntExtra(ArticleListActivity.ARTICLE_ITEM_POSITION, -1)
        val articleIdArray = intent.getStringArrayExtra(ArticleListActivity.ARTICLE_ITEM_ID_ARRAY)
        val adapter = ArticleDetailFragmentAdapter(
            this,
            articleIdArray
        )
        article_detail_view_pager.adapter = adapter
        article_detail_view_pager.setCurrentItem(currentPosition, false)
    }

    private inner class ArticleDetailFragmentAdapter(
        articleDetailActivity: ArticleDetailActivity,
        private val articleIdArray: Array<String>
    ) :
        FragmentStateAdapter(articleDetailActivity) {
        override fun getItemCount(): Int {
            return articleIdArray.size
        }

        override fun createFragment(position: Int): Fragment {
            val bundle = Bundle()
            val articleDetailFragment = ArticleDetailFragment()
            bundle.putString(ARTICLE_ITEM_ID, articleIdArray[position])
            articleDetailFragment.arguments = bundle
            return articleDetailFragment
        }
    }

}