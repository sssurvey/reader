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
import com.haomins.data.util.DateUtils
import com.haomins.reader.adapters.ArticleTitleListPagingAdapter
import com.haomins.reader.databinding.FragmentArticleListBinding
import com.haomins.reader.utils.image.ImageLoaderUtils
import com.haomins.reader.viewModels.ArticleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//TODO: 143 double check later
@AndroidEntryPoint
class LoadAllArticleListFragment :
    Fragment(),
    ArticleTitleListPagingAdapter.ArticleTitleListOnClickListener {

    companion object {
        const val TAG = "LoadAllArticleListFragment"
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
            dateUtils,
            this
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
    }

    override fun onArticleClicked(articleItemId: String) {}

    private fun initializeRecyclerView() {
        binding.articleTitleRecyclerView.apply {
            adapter = articleTitleListPagingAdapter
            layoutManager = recyclerLayoutManager
        }
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
        test()
    }

    private fun test() {
        articleListViewModel.test {
            articleTitleListPagingAdapter.submitData(
                lifecycle,
                it
            )
        }
    }
}