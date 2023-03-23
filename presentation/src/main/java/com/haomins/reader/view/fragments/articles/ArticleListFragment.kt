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
import com.haomins.data.util.DateUtils
import com.haomins.model.entity.ArticleEntity
import com.haomins.reader.adapters.ArticleTitleListAdapter
import com.haomins.reader.databinding.FragmentArticleListBinding
import com.haomins.reader.utils.ImageLoaderUtils
import com.haomins.reader.viewModels.ArticleListViewModel
import dagger.hilt.android.AndroidEntryPoint
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
    lateinit var imageLoaderUtils: ImageLoaderUtils
    @Inject
    lateinit var dateUtils: DateUtils

    private lateinit var currentArticleListViewMode: ArticleListViewMode
    private lateinit var binding: FragmentArticleListBinding

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
        binding.articleTitleRecyclerView.adapter
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
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentArticleListViewMode = arguments?.get(LOAD_MODE_KEY) as ArticleListViewMode
        loadArticleList()
        registerLiveDataObservers()
        binding.articleTitleRecyclerView.apply {
            setHasFixedSize(true)
            adapter = ArticleTitleListAdapter(
                articleTitleUiItems,
                imageLoaderUtils::loadPreviewImage,
                dateUtils,
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

    private fun hideProgressBar() {
        handler.postDelayed({ binding.bottomProgressBar.visibility = View.GONE }, PROGRESS_BAR_DELAY)
    }

    private fun showProgressBar() {
        binding.bottomProgressBar.visibility = View.VISIBLE
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