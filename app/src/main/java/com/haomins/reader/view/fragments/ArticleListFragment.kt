package com.haomins.reader.view.fragments

import android.os.Bundle
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
import com.haomins.reader.view.activities.ArticleListActivity.Companion.SOURCE_FEED_ID
import com.haomins.reader.viewModels.ArticleListViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.article_titile_recycler_view_item.view.*
import kotlinx.android.synthetic.main.fragment_article_list.*
import javax.inject.Inject

class ArticleListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var articleListViewModel: ArticleListViewModel

    private val recyclerLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    private val articleTitleListUiItemObserver by lazy {
        Observer<List<ArticleTitleListUiItem>> {
            article_title_recycler_view.adapter = ArticleTitleListAdapter(it)
        }
    }

    data class ArticleTitleListUiItem(
        val title: String,
        val postTime: String
    )

    companion object {
        const val TAG = "ArticleListFragment"
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
        registerLiveDataObservers()
        article_title_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
        }
        initiateArticleLoad(arguments)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        articleListViewModel.disposeObservers()
    }

    private fun registerLiveDataObservers() {
        articleListViewModel.articleTitleUiItemsList.observe(this, articleTitleListUiItemObserver)
    }

    private fun initiateArticleLoad(bundle: Bundle?) {
        bundle?.getString(SOURCE_FEED_ID)?.let {
            articleListViewModel.loadArticles(it)
        }
    }

    private inner class ArticleTitleListAdapter(private val articleTitleListUiItems: List<ArticleTitleListUiItem>) :
        RecyclerView.Adapter<ArticleTitleListAdapter.CustomViewHolder>() {

        inner class CustomViewHolder(val viewHolder: View) : RecyclerView.ViewHolder(viewHolder)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val articleListItemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.article_titile_recycler_view_item, parent, false)
            return CustomViewHolder(articleListItemView)
        }

        override fun getItemCount(): Int {
            return articleTitleListUiItems.size
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.viewHolder.article_title_text_view.text = articleTitleListUiItems[position].title
            holder.viewHolder.article_publish_time_text_view.text =
                articleTitleListUiItems[position].postTime
        }

    }

}