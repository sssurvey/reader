package com.haomins.reader.view.fragments.articles

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.reader.R
import com.haomins.reader.adapters.ArticleTitleListAdapter
import com.haomins.reader.utils.GlideUtils
import com.haomins.reader.viewModels.ArticleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article_list.*
import javax.inject.Inject

@AndroidEntryPoint
class ArticleListFragment : Fragment(), ArticleTitleListAdapter.ArticleTitleListOnClickListener {

    companion object {
        const val TAG = "ArticleListFragment"
        const val LOAD_MODE_KEY = "ArticleListFragment_LOAD_MODE"
        private const val PROGRESS_BAR_DELAY = 1500L
    }

    enum class ArticleListViewMode(val key: String) {
        LOAD_BY_FEED_ID("LOAD_BY_FEED_ID"),
        LOAD_ALL("LOAD_ALL")
    }

    interface HasClickableArticleList {

        fun startArticleDetailActivity(articleItemId: String, articleItemIdArray: Array<String>)

    }

    @Inject
    lateinit var glideUtils: GlideUtils

    private lateinit var currentArticleListViewMode: ArticleListViewMode

    private val articleListViewModel by viewModels<ArticleListViewModel>()
    private val articleTitleUiItems = mutableListOf<ArticleEntity>()

    private val feedId by lazy { arguments?.getString(currentArticleListViewMode.key).toString() }
    private val handler by lazy { Handler() }
    private val recyclerLayoutManager by lazy { LinearLayoutManager(context) }
    private val isLoadingObserver by lazy {
        Observer<Boolean> {
            if (it) showProgressBar()
            else hideProgressBar()
        }
    }
    private val adapter by lazy {
        article_title_recycler_view.adapter
    }
    private val articleTitleListUiItemObserver by lazy {
        Observer<Set<ArticleEntity>> { set ->
            articleTitleUiItems.clear()
            articleTitleUiItems.addAll(set)
            adapter?.notifyItemInserted(
                (adapter?.itemCount ?: 0) + 1
            )
        }
    }

    private val recyclerViewOnScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) loadMoreArticleRightNow()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentArticleListViewMode = arguments?.get(LOAD_MODE_KEY) as ArticleListViewMode
        loadArticleList()
        registerLiveDataObservers()
        article_title_recycler_view.apply {
            setHasFixedSize(true)
            adapter = ArticleTitleListAdapter(
                articleTitleUiItems,
                glideUtils,
                this@ArticleListFragment
            )
            layoutManager = recyclerLayoutManager
            addOnScrollListener(recyclerViewOnScrollListener)
        }
    }

    override fun onArticleClicked(articleItemId: String) {
        val itemIdList: List<String> = articleTitleUiItems.map { it.itemId }
        activity?.let {
            (it as HasClickableArticleList).startArticleDetailActivity(
                articleItemId,
                itemIdList.toTypedArray()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    //TODO: infinite load feature
    override fun onLoadMoreArticlesBasedOnPosition(position: Int) {
        //        var loadMoreArticleThreshold = (DEFAULT_ARTICLE_AMOUNT * LOAD_MORE_OFFSET_SCALE).toInt()
        //        if (position >= loadMoreArticleThreshold) {
        //        if (position == article_title_recycler_view.adapter?.itemCount) {
        //            when (currentArticleListViewMode) {
        //                ArticleListViewMode.LOAD_BY_FEED_ID -> articleListViewModel.continueLoadArticles(
        //                    feedId
        //                )
        //                ArticleListViewMode.LOAD_ALL -> articleListViewModel.continueLoadAllArticles()
        //            }
        //            loadMoreArticleThreshold += loadMoreArticleThreshold
        //        }
    }

    private fun hideProgressBar() {
        handler.postDelayed({ bottom_progress_bar.visibility = View.GONE }, PROGRESS_BAR_DELAY)
    }

    private fun showProgressBar() {
        bottom_progress_bar.visibility = View.VISIBLE
    }

    private fun registerLiveDataObservers() {
        articleListViewModel.articleTitleUiItemsListLiveData.observe(
            viewLifecycleOwner,
            articleTitleListUiItemObserver
        )
        articleListViewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
    }

    private fun loadArticleList() {
        when (currentArticleListViewMode) {
            ArticleListViewMode.LOAD_ALL -> articleListViewModel.loadAllArticles()
            ArticleListViewMode.LOAD_BY_FEED_ID -> {
                articleListViewModel.loadArticles(feedId)
            }
        }
    }

    private fun loadMoreArticleRightNow() {
        when (currentArticleListViewMode) {
            ArticleListViewMode.LOAD_BY_FEED_ID ->
                articleListViewModel.continueLoadArticles(feedId)
            ArticleListViewMode.LOAD_ALL ->
                articleListViewModel.continueLoadAllArticles()
        }
    }

}