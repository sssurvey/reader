package com.haomins.reader.view.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haomins.data.service.TheOldReaderService.Companion.DEFAULT_ARTICLE_AMOUNT
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.reader.R
import com.haomins.reader.ReaderApplication
import com.haomins.reader.adapters.ArticleTitleListAdapter
import com.haomins.reader.view.activities.ArticleListActivity
import com.haomins.reader.view.activities.ArticleListActivity.Companion.MODE
import com.haomins.reader.view.activities.ArticleListActivity.Mode
import com.haomins.reader.viewModels.ArticleListViewModel
import kotlinx.android.synthetic.main.fragment_article_list.*
import javax.inject.Inject

class ArticleListFragment : Fragment(), ArticleTitleListAdapter.ArticleTitleListOnClickListener {

    companion object {
        const val TAG = "ArticleListFragment"
        private const val PROGRESS_BAR_DELAY = 1500L
        private const val LOAD_MORE_OFFSET_SCALE = 0.7
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val articleListViewModel by viewModels<ArticleListViewModel> { viewModelFactory }
    private lateinit var currentMode: Mode
    private var loadMoreArticleThreshold = (DEFAULT_ARTICLE_AMOUNT * LOAD_MORE_OFFSET_SCALE).toInt()
    private val articleTitleUiItems: MutableList<ArticleEntity> = ArrayList()

    private val feedId by lazy { arguments?.getString(currentMode.key).toString() }
    private val handler by lazy { Handler() }
    private val recyclerLayoutManager by lazy { LinearLayoutManager(context) }
    private val isLoadingObserver by lazy {
        Observer<Boolean> {
            if (it) showProgressBar()
            else hideProgressBar()
        }
    }

    private val articleTitleListUiItemObserver by lazy {
        Observer<List<ArticleEntity>> { list ->
            articleTitleUiItems.apply {
                clear()
                addAll(list)
                sortByDescending { it.itemPublishedMillisecond }
            }
            article_title_recycler_view.adapter?.notifyDataSetChanged()
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

    override fun onAttach(context: Context) {
        (requireActivity().application as ReaderApplication).appComponent.viewModelComponent()
                .build().inject(this)
        super.onAttach(context)
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
        currentMode = arguments?.get(MODE) as Mode
        loadArticleList()
        registerLiveDataObservers()
        article_title_recycler_view.apply {
            setHasFixedSize(true)
            adapter = ArticleTitleListAdapter(
                    articleTitleUiItems,
                    this@ArticleListFragment
            )
            layoutManager = recyclerLayoutManager
            addOnScrollListener(recyclerViewOnScrollListener)
        }
    }

    override fun onArticleAtPositionClicked(position: Int) {
        val itemIdList: List<String> = articleTitleUiItems.map { it.itemId }
        activity?.let {
            (it as ArticleListActivity).startArticleDetailActivity(
                    position,
                    itemIdList.toTypedArray()
            )
        }
    }

    override fun onLoadMoreArticlesBasedOnPosition(position: Int) {
        if (position >= loadMoreArticleThreshold) {
            when (currentMode) {
                Mode.LOAD_BY_FEED_ID -> articleListViewModel.continueLoadArticles(feedId)
                Mode.LOAD_ALL -> articleListViewModel.continueLoadAllArticles()
            }
            loadMoreArticleThreshold += loadMoreArticleThreshold
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    private fun hideProgressBar() {
        handler.postDelayed({ bottom_progress_bar.visibility = View.GONE }, PROGRESS_BAR_DELAY)
    }

    private fun showProgressBar() {
        bottom_progress_bar.visibility = View.VISIBLE
    }

    private fun registerLiveDataObservers() {
        articleListViewModel.articleTitleUiItemsList.observe(
                viewLifecycleOwner,
                articleTitleListUiItemObserver
        )
        articleListViewModel.isLoading.observe(viewLifecycleOwner, isLoadingObserver)
    }

    private fun loadArticleList() {
        when (currentMode) {
            Mode.LOAD_ALL -> articleListViewModel.loadAllArticles()
            Mode.LOAD_BY_FEED_ID -> {
                articleListViewModel.loadArticles(feedId)
            }
        }
    }

    private fun loadMoreArticleRightNow() {
        when (currentMode) {
            Mode.LOAD_BY_FEED_ID -> articleListViewModel.continueLoadArticles(feedId)
            Mode.LOAD_ALL -> articleListViewModel.continueLoadAllArticles()
        }
    }

}