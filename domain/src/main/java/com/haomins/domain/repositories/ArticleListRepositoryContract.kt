package com.haomins.domain.repositories

import com.haomins.domain_model.entities.ArticleEntity
import io.reactivex.Single

interface ArticleListRepositoryContract {

    fun loadArticleItems(feedId: String): Single<List<ArticleEntity>>

    fun continueLoadArticleItems(feedId: String): Single<List<ArticleEntity>>

    fun continueLoadAllArticleItems(): Single<List<ArticleEntity>>

    fun loadAllArticleItems(): Single<List<ArticleEntity>>
}