package com.haomins.reader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.reader.R
import com.haomins.reader.utils.GlideUtils
import kotlinx.android.synthetic.main.article_title_recycler_view_item.view.*

class ArticleTitleListAdapter(
    private val articleTitleListUiItems: List<ArticleEntity>,
    private val glideUtils: GlideUtils,
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
        val readerArticleItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.article_title_recycler_view_item, parent, false)
        return CustomViewHolder(readerArticleItemView)
    }

    override fun getItemCount(): Int {
        return articleTitleListUiItems.size
    }

    override fun onBindViewHolder(holder: ArticleTitleListAdapter.CustomViewHolder, position: Int) {
        with(holder.viewHolder) {
            article_title.text = articleTitleListUiItems[position].itemTitle
            article_posted_time.text = articleTitleListUiItems[position].howLongAgo
            glideUtils.loadIconImage(
                imageView = article_source_icon,
                articleTitleListUiItems[position].href
            )
            setOnClick(this, position)
            glideUtils.loadPreviewImage(
                imageView = article_preview_image,
                articleTitleListUiItems[position].previewImageUrl
            )
        }
        articleTitleListOnClickListener.onLoadMoreArticlesBasedOnPosition(position)
    }

    private fun setOnClick(view: View, position: Int) {
        view.setOnClickListener {
            articleTitleListOnClickListener.onArticleAtPositionClicked(position)
        }
    }

}