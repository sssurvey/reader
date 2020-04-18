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
import com.bumptech.glide.Glide
import com.haomins.reader.R
import com.haomins.reader.view.activities.MainActivity
import com.haomins.reader.viewModels.SourceTitleListViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_source_list_title.*
import kotlinx.android.synthetic.main.source_title_recycler_view_item.view.*
import java.net.URL
import javax.inject.Inject

class SourceTitleListFragment : Fragment() {

    companion object {
        const val TAG = "SourceTitleListFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var sourceTitleListViewModel: SourceTitleListViewModel
    private val sourceListDisplayDataList: MutableList<Pair<String, URL>> = ArrayList()

    private val sourceTitleListAdapter by lazy {
        SourceTitleListAdapter(sourceListDisplayDataList)
    }

    private val sourceListDataSetObserver by lazy {
        Observer<List<Pair<String, URL>>> {
            sourceListDisplayDataList.clear()
            sourceListDisplayDataList.addAll(it)
            sourceTitleListAdapter.notifyDataSetChanged()
        }
    }

    private val recyclerLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_source_list_title, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        sourceTitleListViewModel =
            ViewModelProviders.of(this, viewModelFactory)[SourceTitleListViewModel::class.java]
        registerLiveDataObserver()
        source_title_recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = sourceTitleListAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        sourceTitleListViewModel.loadSourceSubscriptionList()
    }

    private fun registerLiveDataObserver() {
        sourceTitleListViewModel.apply {
            sourceListUiDataSet.observe(
                viewLifecycleOwner,
                sourceListDataSetObserver
            )
        }
    }

    private fun sourceListRecyclerViewItemClickedAt(position: Int) {
        val feedId = sourceTitleListViewModel.getSubSourceId(position)
        activity?.let {
            (it as MainActivity).startArticleListActivity(feedId)
        }
    }

    inner class SourceTitleListAdapter(private val subSourceDisplayItems: List<Pair<String, URL>>) :
        RecyclerView.Adapter<SourceTitleListAdapter.CustomViewHolder>() {

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
            Glide.with(this@SourceTitleListFragment)
                .asDrawable()
                .centerCrop()
                .load(url)
                .into(holder.viewHolder.source_icon_image_view)
        }
    }
}