package com.haomins.reader.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.haomins.reader.R
import com.haomins.reader.ReaderApplication
import com.haomins.reader.adapters.SourceTitleListAdapter
import com.haomins.reader.view.activities.MainActivity
import com.haomins.reader.viewModels.SourceTitleListViewModel
import kotlinx.android.synthetic.main.fragment_source_list_title.*
import java.net.URL
import javax.inject.Inject

class SourceTitleListFragment : Fragment() {

    companion object {
        const val TAG = "SourceTitleListFragment"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val sourceTitleListViewModel by viewModels<SourceTitleListViewModel> { viewModelFactory }
    private val sourceListDisplayDataList: MutableList<Pair<String, URL>> = ArrayList()

    private val sourceTitleListAdapter by lazy {
        SourceTitleListAdapter(
            sourceListDisplayDataList,
            sourceTitleListViewModel,
            ::sourceListRecyclerViewItemClickedAt
        )
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

    override fun onAttach(context: Context) {
        (requireActivity().application as ReaderApplication).appComponent.viewModelComponent()
            .build()
            .inject(this)
        super.onAttach(context)
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
}