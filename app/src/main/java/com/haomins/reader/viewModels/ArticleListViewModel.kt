package com.haomins.reader.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.data.entities.ArticleEntity
import com.haomins.reader.repositories.ArticleListRepository
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
    private val articleListRepository: ArticleListRepository
) : ViewModel() {

    fun loadArticles(feedId: String): LiveData<List<ArticleEntity>> {
        return articleListRepository.loadArticleItemRefs(feedId)
    }

}