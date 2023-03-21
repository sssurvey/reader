package com.haomins.reader.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.haomins.reader.adapters.SourceTitleListAdapter
import com.haomins.reader.databinding.FragmentSourceListTitleBinding
import com.haomins.reader.view.activities.MainActivity
import com.haomins.reader.viewModels.SourceTitleListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SourceTitleListFragment : Fragment() {

    companion object {
        const val TAG = "SourceTitleListFragment"
    }

    private val sourceTitleListViewModel by viewModels<SourceTitleListViewModel>()
    private val sourceListDisplayDataList: MutableList<SourceTitleListViewModel.SourceListUi> =
        mutableListOf()
    private lateinit var binding: FragmentSourceListTitleBinding

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
    ): View {
        binding = FragmentSourceListTitleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerLiveDataObserver()
        initiateRecyclerView()
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

    private fun initiateRecyclerView() {
        with(binding) {
            sourceTitleRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = sourceTitleListAdapter
            }
        }
    }

    private fun sourceListRecyclerViewItemClickedAt(
        itemType: SourceTitleListViewModel.TYPE,
        id: String
    ) {
        when (itemType) {
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