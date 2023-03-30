package com.haomins.reader.view.fragments.articles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.haomins.data.util.DateUtils
import com.haomins.reader.adapters.ArticleTitleListPagingAdapter
import com.haomins.reader.databinding.FragmentArticleListBinding
import com.haomins.reader.utils.image.ImageLoaderUtils
import com.haomins.reader.viewModels.ArticleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AllArticleListFragment : Fragment() {

    companion object {
        const val TAG = "LoadAllArticleListFragment"

        fun getInstance(): AllArticleListFragment {
            return AllArticleListFragment()
        }
    }

    @Inject
    lateinit var imageLoaderUtils: ImageLoaderUtils

    @Inject
    lateinit var dateUtils: DateUtils

    private lateinit var binding: FragmentArticleListBinding
    private val articleListViewModel by viewModels<ArticleListViewModel>()
    private val recyclerLayoutManager by lazy { LinearLayoutManager(context) }
    private val articleTitleListPagingAdapter by lazy {
        ArticleTitleListPagingAdapter(
            imageLoaderUtils::loadPreviewImage,
            this::onArticleLicked,
            dateUtils
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecyclerView()
        startLoading()
    }

    private fun onArticleLicked(articleItemId: String) {
        val activity = requireActivity()
        if (activity is HasClickableArticleList) {
            activity.startArticleDetailActivity(
                articleItemId,
                articleTitleListPagingAdapter.snapshot().items.map { it.itemId }.toTypedArray()
            )
        }
    }

    private fun initializeRecyclerView() {
        binding.articleTitleRecyclerView.apply {
            adapter = articleTitleListPagingAdapter
            layoutManager = recyclerLayoutManager
        }
        // reset recycler view position when latest item is loaded from remote
        articleTitleListPagingAdapter.registerAdapterDataObserver(
            object : RecyclerView.AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    if (positionStart == 0) recyclerLayoutManager.scrollToPosition(0)
                }
            }
        )
        articleTitleListPagingAdapter.addLoadStateListener { loadState ->
            // Config error UI behavior
            run {
                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.error?.localizedMessage?.let { Log.d(TAG, it) }
            }
            // Config loading UI behavior
            run {
                if (
                    loadState.append == LoadState.Loading
                    || loadState.refresh == LoadState.Loading
                    || loadState.prepend == LoadState.Loading
                ) binding.bottomProgressBar.visibility = View.VISIBLE
                else binding.bottomProgressBar.visibility = View.INVISIBLE
            }
        }
    }

    private fun startLoading() {
        articleListViewModel.loadAllArticles {
            articleTitleListPagingAdapter.submitData(
                lifecycle,
                it
            )
        }
    }
}