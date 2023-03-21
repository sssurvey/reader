package com.haomins.reader.adapters

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haomins.reader.R
import com.haomins.reader.databinding.SourceTitleRecyclerViewItemBinding
import com.haomins.reader.viewModels.SourceTitleListViewModel
import java.net.URL

class SourceTitleListAdapter(
    private val subSourceDisplayItems: List<SourceTitleListViewModel.SourceListUi>,
    private val sourceTitleListViewModel: SourceTitleListViewModel,
    private val onRowItemClicked: (type: SourceTitleListViewModel.TYPE, id: String) -> Unit,
    private val application: Application
) : RecyclerView.Adapter<SourceTitleListAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val binding: SourceTitleRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemBinding = SourceTitleRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return subSourceDisplayItems.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        subSourceDisplayItems[position].customizeViewHolder(holder)
    }

    private fun SourceTitleListViewModel.SourceListUi.customizeViewHolder(holder: CustomViewHolder) {
        when (type) {
            /*
            Set custom view holder for RSS sources.
             */
            SourceTitleListViewModel.TYPE.RSS_SOURCE -> {
                holder.binding.sourceTitleTextView.text = name
                sourceIconUrl?.let { loadIconImage(holder, it) }
                setOnClick(holder, type, id)
            }
            /*
            Set custom view holder for MENU OPTIONS. e.g: ALL Articles, bookmarks etc.
             */
            SourceTitleListViewModel.TYPE.ALL_ITEMS_OPTION -> {
                holder.setIsRecyclable(false)
                holder.binding.sourceTitleTextView.textAlignment =
                    View.TEXT_ALIGNMENT_TEXT_END
                holder.binding.sourceIconImageView.visibility = View.INVISIBLE
                holder.binding.sourceTitleTextView.text = name
                holder.binding.sourceTitleTextView.textSize = 30.0f
                holder.binding.sourceTitleTextView.setTextColor(
                    application.resources.getColor(R.color.colorAccent, null)
                ) //TODO: to be implemented
                setOnClick(holder, type, id)
            }
            SourceTitleListViewModel.TYPE.ADD_SOURCE_OPTION -> {
                holder.setIsRecyclable(false)
                holder.binding.sourceTitleTextView.textAlignment =
                    View.TEXT_ALIGNMENT_TEXT_END
                holder.binding.sourceIconImageView.visibility = View.INVISIBLE
                holder.binding.sourceTitleTextView.text = name
                holder.binding.sourceTitleTextView.textSize = 30.0f
                holder.binding.sourceTitleTextView.setTextColor(
                    application.resources.getColor(R.color.colorPrimary, null)
                ) //TODO: to be implemented
                setOnClick(holder, type, id)
            }
            SourceTitleListViewModel.TYPE.SETTINGS_OPTION -> {
                holder.setIsRecyclable(false)
                holder.binding.sourceTitleTextView.textAlignment =
                    View.TEXT_ALIGNMENT_TEXT_END
                holder.binding.sourceIconImageView.visibility = View.INVISIBLE
                holder.binding.sourceTitleTextView.text = name
                holder.binding.sourceTitleTextView.textSize = 30.0f
                setOnClick(holder, type, id)
            } //TODO: to be implemented
            SourceTitleListViewModel.TYPE.SUMMARY_OPTION -> {
                holder.setIsRecyclable(false)
                holder.binding.sourceTitleTextView.textAlignment =
                    View.TEXT_ALIGNMENT_TEXT_END
                holder.binding.sourceIconImageView.visibility = View.INVISIBLE
                holder.binding.sourceTitleTextView.text = name
                holder.binding.sourceTitleTextView.textSize = 20.0f
                setOnClick(holder, type, id)
            } //TODO: to be implemented
        }
    }

    private fun setOnClick(
        holder: CustomViewHolder,
        type: SourceTitleListViewModel.TYPE,
        id: String
    ) {
        holder.binding.root.setOnClickListener {
            sourceListRecyclerViewItemClickedAt(type, id)
        }
    }

    private fun loadIconImage(holder: CustomViewHolder, url: URL) {
        sourceTitleListViewModel.loadImageIcon(
            imageView = holder.binding.sourceIconImageView,
            url = url
        )
    }

    private fun sourceListRecyclerViewItemClickedAt(
        itemType: SourceTitleListViewModel.TYPE,
        id: String
    ) {
        onRowItemClicked(itemType, id)
    }
}