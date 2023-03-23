package com.haomins.reader.adapters

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.haomins.reader.R
import com.haomins.reader.databinding.SubscriptionRecyclerViewItemBinding
import com.haomins.reader.viewModels.SubscriptionListViewModel

class SubscriptionListAdapter(
    private val subscriptionDisplayItems: List<SubscriptionListViewModel.SubscriptionListUi>,
    private val iconImageLoader: (ImageView, String) -> Unit,
    private val onRowItemClicked: (type: SubscriptionListViewModel.TYPE, id: String) -> Unit,
    private val application: Application
) : RecyclerView.Adapter<SubscriptionListAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val binding: SubscriptionRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemBinding = SubscriptionRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return subscriptionDisplayItems.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        subscriptionDisplayItems[position].customizeViewHolder(holder)
    }

    private fun SubscriptionListViewModel.SubscriptionListUi.customizeViewHolder(holder: CustomViewHolder) {
        when (type) {
            /*
            Set custom view holder for RSS subscriptions.
             */
            SubscriptionListViewModel.TYPE.RSS_SOURCE -> {
                holder.binding.subscriptionTitleTextView.text = name
                sourceIconUrl?.let {
                    iconImageLoader.invoke(holder.binding.subscriptionIconImageView, it)
                }
                setOnClick(holder, type, id)
            }
            /*
            Set custom view holder for MENU OPTIONS. e.g: ALL Articles, bookmarks etc.
             */
            SubscriptionListViewModel.TYPE.ALL_ITEMS_OPTION -> {
                holder.setIsRecyclable(false)
                holder.binding.subscriptionTitleTextView.textAlignment =
                    View.TEXT_ALIGNMENT_TEXT_END
                holder.binding.subscriptionIconImageView.visibility = View.INVISIBLE
                holder.binding.subscriptionTitleTextView.text = name
                holder.binding.subscriptionTitleTextView.textSize = 30.0f
                holder.binding.subscriptionTitleTextView.setTextColor(
                    application.resources.getColor(R.color.colorAccent, null)
                ) //TODO: to be implemented
                setOnClick(holder, type, id)
            }
            SubscriptionListViewModel.TYPE.ADD_SOURCE_OPTION -> {
                holder.setIsRecyclable(false)
                holder.binding.subscriptionTitleTextView.textAlignment =
                    View.TEXT_ALIGNMENT_TEXT_END
                holder.binding.subscriptionIconImageView.visibility = View.INVISIBLE
                holder.binding.subscriptionTitleTextView.text = name
                holder.binding.subscriptionTitleTextView.textSize = 30.0f
                holder.binding.subscriptionTitleTextView.setTextColor(
                    application.resources.getColor(R.color.colorPrimary, null)
                ) //TODO: to be implemented
                setOnClick(holder, type, id)
            }
            SubscriptionListViewModel.TYPE.SETTINGS_OPTION -> {
                holder.setIsRecyclable(false)
                holder.binding.subscriptionTitleTextView.textAlignment =
                    View.TEXT_ALIGNMENT_TEXT_END
                holder.binding.subscriptionIconImageView.visibility = View.INVISIBLE
                holder.binding.subscriptionTitleTextView.text = name
                holder.binding.subscriptionTitleTextView.textSize = 30.0f
                setOnClick(holder, type, id)
            } //TODO: to be implemented
            SubscriptionListViewModel.TYPE.SUMMARY_OPTION -> {
                holder.setIsRecyclable(false)
                holder.binding.subscriptionTitleTextView.textAlignment =
                    View.TEXT_ALIGNMENT_TEXT_END
                holder.binding.subscriptionIconImageView.visibility = View.INVISIBLE
                holder.binding.subscriptionTitleTextView.text = name
                holder.binding.subscriptionTitleTextView.textSize = 20.0f
                setOnClick(holder, type, id)
            } //TODO: to be implemented
        }
    }

    private fun setOnClick(
        holder: CustomViewHolder,
        type: SubscriptionListViewModel.TYPE,
        id: String
    ) {
        holder.binding.root.setOnClickListener {
            subscriptionListRecyclerViewItemClickedAt(type, id)
        }
    }

    private fun subscriptionListRecyclerViewItemClickedAt(
        itemType: SubscriptionListViewModel.TYPE,
        id: String
    ) {
        onRowItemClicked(itemType, id)
    }
}