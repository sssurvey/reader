package com.haomins.reader.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.haomins.reader.adapters.SubscriptionListAdapter
import com.haomins.reader.databinding.FragmentSubscriptionListBinding
import com.haomins.reader.view.activities.MainActivity
import com.haomins.reader.viewModels.SubscriptionListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SubscriptionListFragment : Fragment() {

    companion object {
        const val TAG = "SubscriptionListFragment"
    }

    private val subscriptionListViewModel by viewModels<SubscriptionListViewModel>()
    private val subscriptionListDisplayDataList: MutableList<SubscriptionListViewModel.SubscriptionListUi> =
        mutableListOf()
    private lateinit var binding: FragmentSubscriptionListBinding

    private val subscriptionListAdapter by lazy {
        SubscriptionListAdapter(
            subscriptionDisplayItems = subscriptionListDisplayDataList,
            subscriptionListViewModel = subscriptionListViewModel,
            onRowItemClicked = ::sourceListRecyclerViewItemClickedAt,
            application = activity?.application!!
        )
    }

    private val subscriptionListDataSetObserver by lazy {
        Observer<List<SubscriptionListViewModel.SubscriptionListUi>> {
            subscriptionListDisplayDataList.clear()
            subscriptionListDisplayDataList.addAll(it)
            subscriptionListAdapter.notifyDataSetChanged()
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
        binding = FragmentSubscriptionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerLiveDataObserver()
        initiateRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        subscriptionListViewModel.loadSubscriptionList()
    }

    private fun registerLiveDataObserver() {
        subscriptionListViewModel.apply {
            subscriptionListUiDataSet.observe(
                viewLifecycleOwner,
                subscriptionListDataSetObserver
            )
        }
    }

    private fun initiateRecyclerView() {
        with(binding) {
            sourceTitleRecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = recyclerLayoutManager
                adapter = subscriptionListAdapter
            }
        }
    }

    private fun sourceListRecyclerViewItemClickedAt(
        itemType: SubscriptionListViewModel.TYPE,
        id: String
    ) {
        when (itemType) {
            SubscriptionListViewModel.TYPE.RSS_SOURCE -> {
                activity?.let {
                    (it as MainActivity)
                        .startArticleListActivity(id)
                }
            }
            SubscriptionListViewModel.TYPE.ALL_ITEMS_OPTION -> {
                activity?.let {
                    (it as MainActivity)
                        .startArticleListActivityForAllItems()
                }
            }
            SubscriptionListViewModel.TYPE.ADD_SOURCE_OPTION -> {
                activity?.let {
                    (it as MainActivity)
                        .startAddSourceActivity()
                }
            }
            SubscriptionListViewModel.TYPE.SETTINGS_OPTION -> {
                activity?.let {
                    (it as MainActivity)
                        .startSettingsActivity()
                }
            }
            SubscriptionListViewModel.TYPE.SUMMARY_OPTION -> {}
        }

    }
}