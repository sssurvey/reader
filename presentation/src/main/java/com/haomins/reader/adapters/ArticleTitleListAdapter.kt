package com.haomins.reader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.reader.R
import kotlinx.android.synthetic.main.article_title_recycler_view_item.view.*

class ArticleTitleListAdapter(
        private val articleTitleListUiItems: List<ArticleEntity>,
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
        holder.viewHolder.article_title_text_view.text = articleTitleListUiItems[position].itemTitle
        holder.viewHolder.article_publish_time_text_view.text =
                articleTitleListUiItems[position].howLongAgo
        setOnClick(holder, position)
        articleTitleListOnClickListener.onLoadMoreArticlesBasedOnPosition(position)
    }

    private fun setOnClick(holder: ArticleTitleListAdapter.CustomViewHolder, position: Int) {
        holder.viewHolder.setOnClickListener {
            articleTitleListOnClickListener.onArticleAtPositionClicked(position)
        }
    }

}