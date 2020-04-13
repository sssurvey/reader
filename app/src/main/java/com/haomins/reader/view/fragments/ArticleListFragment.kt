package com.haomins.reader.view.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haomins.reader.R
import com.haomins.reader.view.activities.ArticleListActivity
import com.haomins.reader.view.activities.ArticleListActivity.Companion.LOAD_ALL_ITEM
import com.haomins.reader.view.activities.ArticleListActivity.Companion.SOURCE_FEED_ID
import com.haomins.reader.viewModels.ArticleListViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.article_title_recycler_view_item.view.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import javax.inject.Inject

class ArticleListFragment : Fragment() {

    companion object {

        const val TAG = "ArticleListFragment"

        private const val PROGRESS_BAR_DELAY = 1500L
    }

    data class ArticleTitleListUiItem(
        val title: String,
        val postTime: String,
        val _postTimeMillisecond: Long,
        val _itemId: String
    )

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var articleListViewModel: ArticleListViewModel
    private lateinit var feedId: String

    private var isLoadAllArticles = false
    private val articleTitleUiItems: MutableList<ArticleTitleListUiItem> = ArrayList()

    private val handler by lazy {
        Handler()
    }

    private val recyclerLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    private val isLoadingObserver by lazy {
        Observer<Boolean> {
            if (it) showProgressBar()
            else hideProgressBar()
        }
    }

    private val articleTitleListUiItemObserver by lazy {
        Observer<List<ArticleTitleListUiItem>> { list ->
            articleTitleUiItems.apply {
                clear()
                addAll(list)
                sortByDescending { it._postTimeMillisecond }
            }
            article_title_recycler_view.adapter?.notifyDataSetChanged()
        }
    }

    private val recyclerViewOnScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) loadMoreArticles()
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
        AndroidSupportInjection.inject(this)
        articleListViewModel =
            ViewModelProviders.of(this, viewModelFactory)[ArticleListViewModel::class.java]
        loadArticleList(arguments)
        registerLiveDataObservers()
        article_title_recycler_view.apply {
            setHasFixedSize(true)
            adapter = ArticleTitleListAdapter(articleTitleUiItems)
            layoutManager = recyclerLayoutManager
            addOnScrollListener(recyclerViewOnScrollListener)
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
        articleListViewModel.articleTitleUiItemsList.observe(this, articleTitleListUiItemObserver)
        articleListViewModel.isLoading.observe(this, isLoadingObserver)
    }

    private fun loadArticleList(bundle: Bundle?) {
        bundle?.let {
            if (it.containsKey(LOAD_ALL_ITEM) && it.getBoolean(LOAD_ALL_ITEM)) {
                isLoadAllArticles = true
                articleListViewModel.loadAllArticles()
            } else {
                it.getString(SOURCE_FEED_ID)?.let {string ->
                    feedId = string
                    articleListViewModel.loadArticles(feedId)
                }
            }
        }
    }

    private fun articleTitleListRecyclerViewItemClickedAt(position: Int) {
        val itemIdList: List<String> = articleTitleUiItems.map {
            it._itemId
        }
        activity?.let {
            (it as ArticleListActivity).startArticleDetailActivity(
                position,
                itemIdList.toTypedArray()
            )
        }
    }

    private fun loadMoreArticles() {
        if (isLoadAllArticles) articleListViewModel.continueLoadAllArticles()
        else articleListViewModel.continueLoadArticles(feedId)
    }

    private inner class ArticleTitleListAdapter(private val articleTitleListUiItems: List<ArticleTitleListUiItem>) :
        RecyclerView.Adapter<ArticleTitleListAdapter.CustomViewHolder>() {

        inner class CustomViewHolder(val viewHolder: View) : RecyclerView.ViewHolder(viewHolder)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val articleListItemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.article_title_recycler_view_item, parent, false)
            return CustomViewHolder(articleListItemView)
        }

        override fun getItemCount(): Int {
            return articleTitleListUiItems.size
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.viewHolder.article_title_text_view.text = articleTitleListUiItems[position].title
            holder.viewHolder.article_publish_time_text_view.text =
                articleTitleListUiItems[position].postTime
            setOnClick(holder, position)
        }

        private fun setOnClick(holder: CustomViewHolder, position: Int) {
            holder.viewHolder.setOnClickListener {
                articleTitleListRecyclerViewItemClickedAt(position)
            }
        }

    }

}