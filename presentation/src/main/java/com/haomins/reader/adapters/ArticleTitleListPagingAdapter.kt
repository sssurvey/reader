package com.haomins.reader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.haomins.data.util.DateUtils
import com.haomins.model.entity.ArticleEntity
import com.haomins.reader.databinding.ArticleTitleRecyclerViewItemBinding

//TODO: 143 double check later
class ArticleTitleListPagingAdapter(
    private val previewImageLoader: (ImageView, String) -> Unit,
    private val dateUtils: DateUtils,
    private val articleTitleListOnClickListener: ArticleTitleListOnClickListener
) : PagingDataAdapter<ArticleEntity, ArticleTitleListPagingAdapter.CustomViewHolder>(
    diffCallback = ARTICLE_ENTITY_COMPARATOR
) {

    interface ArticleTitleListOnClickListener {

        fun onArticleClicked(articleItemId: String)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomViewHolder {
        val itemBinding = ArticleTitleRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
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

        fun bind(articleEntity: ArticleEntity) {
            with(articleEntity) {
                articleTitleTextView.text = itemTitle
                articlePostedTimeTextView.text = dateUtils.howLongAgo(itemPublishedMillisecond)

                // config image view
                run {
                    if (previewImageUrl.isNotEmpty()) {
                        previewImageLoader.invoke(
                            articlePreviewImageView,
                            previewImageUrl
                        )
                    } else {
                        articlePreviewImageView.visibility = View.INVISIBLE
                    }
                }

                itemRootView.setOnClickListener {
                    articleTitleListOnClickListener.onArticleClicked(itemId)
                }
            }
        }
    }

    companion object {
        private val ARTICLE_ENTITY_COMPARATOR = object : DiffUtil.ItemCallback<ArticleEntity>() {
            override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity) =
                oldItem.itemId == newItem.itemId

            override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity) =
                oldItem == newItem
        }
    }
}