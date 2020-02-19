package com.haomins.reader.viewModels

import androidx.lifecycle.ViewModel
import com.haomins.reader.repositories.ArticleDetailRepository
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
    private val articleDetailRepository: ArticleDetailRepository
): ViewModel() {

    fun loadArticleDetail(itemId: String) {
        articleDetailRepository.loadArticleDetail(itemId)
    }

}