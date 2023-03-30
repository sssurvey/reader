package com.haomins.reader.view.activities

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.haomins.reader.databinding.ActivityArticleDetailBinding
import com.haomins.reader.utils.slideInAnimation
import com.haomins.reader.utils.slideOutAnimation
import com.haomins.reader.view.fragments.articles.ArticleDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailActivity : AppCompatActivity() {

    companion object {
        const val ARTICLE_ITEM_ID = "ARTICLE_ITEM_ID"
        private const val TAG = "ArticleDetailActivity"
    }

    private var currentPosition: Int = -1
    private val articleIdArray by lazy {
        intent.getStringArrayExtra(ArticleListActivity.ARTICLE_ITEM_ID_ARRAY)
    }
    private val articleId by lazy {
        intent.getStringExtra(ArticleListActivity.ARTICLE_ITEM_ID)
    }
    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateView()
        slideInAnimation()
        initViewPager()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideOutAnimation()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d(TAG, "onKeyDown :: keyCode is $keyCode, view pager position is $currentPosition")
        when (keyCode) {
            KeyEvent.KEYCODE_VOLUME_DOWN -> {
                currentPosition += 1
                binding.articleDetailViewPager.setCurrentItem(currentPosition, true)
            }
            KeyEvent.KEYCODE_VOLUME_UP -> {
                if (currentPosition > 0) {
                    currentPosition -= 1
                    binding.articleDetailViewPager.setCurrentItem(currentPosition, true)
                }
            }
            else -> {
                super.onKeyDown(keyCode, event)
            }
        }
        return true
    }

    private fun initViewPager() {
        articleIdArray?.let {
            currentPosition = it.indexOf(articleId)
            val adapter = ArticleDetailFragmentAdapter(
                this,
                it
            )
            binding.articleDetailViewPager.adapter = adapter
            binding.articleDetailViewPager.setCurrentItem(currentPosition, false)
        }
    }

    private fun inflateView() {
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
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