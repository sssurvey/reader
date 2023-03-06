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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_source_list_title.*
import javax.inject.Inject

@AndroidEntryPoint
class SourceTitleListFragment : Fragment() {

    companion object {
        const val TAG = "SourceTitleListFragment"
    }

    private val sourceTitleListViewModel by viewModels<SourceTitleListViewModel>()
    private val sourceListDisplayDataList: MutableList<SourceTitleListViewModel.SourceListUi> = mutableListOf()

    private val sourceTitleListAdapter by lazy {
        SourceTitleListAdapter(
            subSourceDisplayItems = sourceListDisplayDataList,
            sourceTitleListViewModel = sourceTitleListViewModel,
            onRowItemClicked = ::sourceListRecyclerViewItemClickedAt,
            application = activity?.application!!
        )
    }

    private val sourceListDataSetObserver by lazy {
        Observer<List<SourceTitleListViewModel.SourceListUi>> {
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

    private fun sourceListRecyclerViewItemClickedAt(itemType: SourceTitleListViewModel.TYPE, id: String) {
        when(itemType) {
            SourceTitleListViewModel.TYPE.RSS_SOURCE -> {
                activity?.let {
                    (it as MainActivity)
                        .startArticleListActivity(id)
                }
            }
            SourceTitleListViewModel.TYPE.ALL_ITEMS_OPTION -> {
                activity?.let {
                    (it as MainActivity)
                        .startArticleListActivityForAllItems()
                }
            }
            SourceTitleListViewModel.TYPE.ADD_SOURCE_OPTION -> {
                activity?.let {
                    (it as MainActivity)
                        .startAddSourceActivity()
                }
            }
            SourceTitleListViewModel.TYPE.SETTINGS_OPTION -> {
                activity?.let {
                    (it as MainActivity)
                        .startSettingsActivity()
                }
            }
            SourceTitleListViewModel.TYPE.SUMMARY_OPTION -> {}
        }

    }
}