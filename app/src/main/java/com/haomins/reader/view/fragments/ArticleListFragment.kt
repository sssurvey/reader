package com.haomins.reader.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.R
import com.haomins.reader.viewModels.ArticleListViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ArticleListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var articleListViewModel: ArticleListViewModel

    companion object {
        const val TAG = "ArticleListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidSupportInjection.inject(this)
        articleListViewModel = ViewModelProviders.of(this, viewModelFactory)[ArticleListViewModel::class.java]
        articleListViewModel.sayHi()
    }

}