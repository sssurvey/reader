package com.haomins.reader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haomins.reader.R
import com.haomins.reader.view.fragments.ArticleListFragment
import kotlinx.android.synthetic.main.article_title_recycler_view_item.view.*

class ArticleTitleListAdapter(
    private val articleTitleListUiItems: List<ArticleListFragment.ArticleTitleListUiItem>,
    private val articleTitleListOnClickListener: ArticleTitleListOnClickListener
) :
    RecyclerView.Adapter<ArticleTitleListAdapter.CustomViewHolder>() {

    interface ArticleTitleListOnClickListener {

        fun onArticleAtPositionClicked(position: Int)

        fun onLoadMoreArticlesBasedOnPosition(position: Int)

    }

    inner class CustomViewHolder(val viewHolder: View) : RecyclerView.ViewHolder(viewHolder)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleTitleListAdapter.CustomViewHolder {
        val articleListItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_title_recycler_view_item, parent, false)
        return CustomViewHolder(articleListItemView)
    }

    override fun getItemCount(): Int {
        return articleTitleListUiItems.size
    }

    override fun onBindViewHolder(holder: ArticleTitleListAdapter.CustomViewHolder, position: Int) {
        holder.viewHolder.article_title_text_view.text = articleTitleListUiItems[position].title
        holder.viewHolder.article_publish_time_text_view.text =
            articleTitleListUiItems[position].postTime
        setOnClick(holder, position)
        articleTitleListOnClickListener.onLoadMoreArticlesBasedOnPosition(position)
    }

    private fun setOnClick(holder: ArticleTitleListAdapter.CustomViewHolder, position: Int) {
        holder.viewHolder.setOnClickListener {
            articleTitleListOnClickListener.onArticleAtPositionClicked(position)
        }
    }

}