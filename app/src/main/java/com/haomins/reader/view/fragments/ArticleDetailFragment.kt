package com.haomins.reader.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.R
import com.haomins.reader.utils.showToast
import com.haomins.reader.view.activities.ArticleListActivity
import com.haomins.reader.viewModels.ArticleDetailViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_article_detail.*
import javax.inject.Inject

class ArticleDetailFragment : Fragment() {

    companion object {
        const val TAG = "ArticleDetailFragment"
    }

    data class ArticleDetailUiItem(
        val title: String,
        val author: String,
        val updateTime: String,
        val contentHtmlData: String
    )

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var articleDetailViewModel: ArticleDetailViewModel
    private lateinit var articleItemIdArray: Array<String>
    private var currentArticlePosition = -1

    private val contentDataObserver by lazy {
        Observer<ArticleDetailUiItem> {
            //TODO, handle the data we loaded
            showToast(it.title)
            article_detail_title_text_view.text = it.title
            article_detail_author_text_view.text = it.author
            article_detail_update_time_text_view.text = it.updateTime
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        articleDetailViewModel = ViewModelProviders.of(this, viewModelFactory)[ArticleDetailViewModel::class.java]
        loadArticleId(arguments)
        showArticle(currentArticlePosition)
        registerLiveDataObservers()
    }

    private fun registerLiveDataObservers() {
        articleDetailViewModel.contentDataForDisplay.observe(this, contentDataObserver)
    }

    private fun loadArticleId(bundle: Bundle?) {
        bundle?.let {
            currentArticlePosition = it.getInt(ArticleListActivity.ARTICLE_ITEM_POSITION)
            articleItemIdArray = it.getStringArray(ArticleListActivity.ARTICLE_ITEM_ID_ARRAY)
        }
    }

    private fun showArticle(position: Int) {
        articleDetailViewModel.loadArticleDetail(articleItemIdArray[position])
    }

}