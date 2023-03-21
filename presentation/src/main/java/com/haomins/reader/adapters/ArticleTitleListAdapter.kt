package com.haomins.reader.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.reader.databinding.ArticleTitleRecyclerViewItemBinding
import com.haomins.reader.utils.GlideUtils

class ArticleTitleListAdapter(
    private val articleTitleListUiItems: List<ArticleEntity>,
    private val glideUtils: GlideUtils,
    private val articleTitleListOnClickListener: ArticleTitleListOnClickListener
) :
    RecyclerView.Adapter<ArticleTitleListAdapter.CustomViewHolder>() {

    interface ArticleTitleListOnClickListener {

        fun onArticleClicked(articleItemId: String)

        fun onLoadMoreArticlesBasedOnPosition(position: Int)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleTitleListAdapter.CustomViewHolder {
        val itemBinding = ArticleTitleRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return articleTitleListUiItems.size
    }

    override fun onBindViewHolder(holder: ArticleTitleListAdapter.CustomViewHolder, position: Int) {
        holder.bind(articleTitleListUiItems[position], position)
        articleTitleListOnClickListener.onLoadMoreArticlesBasedOnPosition(position)
    }

    inner class CustomViewHolder(val binding: ArticleTitleRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val articleTitleTextView: TextView
        private val articlePostedTimeTextView: TextView
        private val articlePreviewImageView: ImageView
        private val itemRootView: ConstraintLayout

        init {
            articleTitleTextView = binding.articleTitle
            articlePostedTimeTextView = binding.articlePostedTime
            articlePreviewImageView = binding.articlePreviewImage
            itemRootView = binding.root
        }

        fun bind(articleEntity: ArticleEntity, position: Int) {
            with(articleEntity) {
                articleTitleTextView.text = itemTitle
                articlePostedTimeTextView.text = howLongAgo
                glideUtils.loadPreviewImage(
                    articlePreviewImageView,
                    previewImageUrl
                )
                itemRootView.setOnClickListener {
                    articleTitleListOnClickListener.onArticleClicked(itemId)
                }
            }
        }
    }

}