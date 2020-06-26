package com.haomins.reader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haomins.reader.R
import com.haomins.reader.viewModels.SourceTitleListViewModel
import kotlinx.android.synthetic.main.source_title_recycler_view_item.view.*
import java.net.URL

class SourceTitleListAdapter(
    private val subSourceDisplayItems: List<Pair<String, URL>>,
    private val sourceTitleListViewModel: SourceTitleListViewModel,
    private val onRowItemClicked: (pos: Int) -> Unit
) : RecyclerView.Adapter<SourceTitleListAdapter.CustomViewHolder>() {

    inner class CustomViewHolder(val viewHolder: View) : RecyclerView.ViewHolder(viewHolder)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val sourceListItemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.source_title_recycler_view_item, parent, false)
        return CustomViewHolder(sourceListItemView)
    }

    override fun getItemCount(): Int {
        return subSourceDisplayItems.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.viewHolder.source_title_text_view.text = subSourceDisplayItems[position].first
        loadIconImage(holder, subSourceDisplayItems[position].second)
        setOnClick(holder, position)
    }

    private fun setOnClick(holder: CustomViewHolder, position: Int) {
        holder.viewHolder.setOnClickListener {
            sourceListRecyclerViewItemClickedAt(position)
        }
    }

    private fun loadIconImage(holder: CustomViewHolder, url: URL) {
        sourceTitleListViewModel.loadImageIcon(
            imageView = holder.viewHolder.source_icon_image_view,
            url = url
        )
    }

    private fun sourceListRecyclerViewItemClickedAt(position: Int) {
        onRowItemClicked(position)
    }
}