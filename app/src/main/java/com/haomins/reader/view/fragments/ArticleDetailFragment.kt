package com.haomins.reader.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.haomins.reader.R
import com.haomins.reader.utils.showToast
import com.haomins.reader.view.activities.ArticleListActivity

class ArticleDetailFragment : Fragment() {

    companion object {
        const val TAG = "ArticleDetailFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadArticleDetail(arguments)
    }

    private fun loadArticleDetail(bundle: Bundle?) {
        bundle?.let {
            showToast(it.getInt(ArticleListActivity.ARTICLE_ITEM_POSITION).toString())
            showToast(it.getStringArray(ArticleListActivity.ARTICLE_ITEM_ID_ARRAY).toString())
        }
    }
    
}