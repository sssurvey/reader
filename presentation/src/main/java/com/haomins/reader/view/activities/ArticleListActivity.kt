package com.haomins.reader.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.haomins.reader.R
import com.haomins.reader.databinding.ActivityArticleListBinding
import com.haomins.reader.utils.slideInAnimation
import com.haomins.reader.utils.slideOutAnimation
import com.haomins.reader.view.fragments.articles.HasClickableArticleList
import com.haomins.reader.view.fragments.articles.LoadAllArticleListFragment
import com.haomins.reader.view.fragments.articles.LoadArticleListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListActivity : AppCompatActivity(), HasClickableArticleList {

    companion object {
        const val ARTICLE_ITEM_ID = "ARTICLE_ITEM_ID"
        const val ARTICLE_ITEM_ID_ARRAY = "ARTICLE_ITEM_ID_ARRAY"
        const val LOAD_MODE_KEY = "LOAD_MODE_KEY"
        private const val TAG = "ArticleListActivity"
    }

    enum class LoadMode(val key: String) {
        LOAD_ALL("LOAD_ALL"),
        LOAD_BY_FEED("LOAD_BY_FEED")
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
            LoadMode.LOAD_BY_FEED -> loadAllArticlesFromFeed()
            LoadMode.LOAD_ALL -> loadAllArticles()
        }
    }

    //TODO: 143 double check later
    private fun loadAllArticles() {
        Log.d(TAG, "::showLoadAllArticleListFragment")
        val bundle = Bundle()
        val loadAllArticleListFragment = LoadAllArticleListFragment()
        loadAllArticleListFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(
            R.id.article_list_activity_frame_layout,
            loadAllArticleListFragment, LoadAllArticleListFragment.TAG
        ).commit()
    }

    private fun loadAllArticlesFromFeed() {
        Log.d(TAG, "::showLoadArticleListFragment")
        supportFragmentManager.commit {
            intent.getStringExtra(LoadMode.LOAD_BY_FEED.key)?.let {
                setReorderingAllowed(true)
                replace(
                    R.id.article_list_activity_frame_layout,
                    LoadArticleListFragment.getInstance(it)
                )
            }
        }
    }

    private fun inflateView() {
        binding = ActivityArticleListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}