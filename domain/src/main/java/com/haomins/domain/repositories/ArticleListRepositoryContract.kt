package com.haomins.domain.repositories

import com.haomins.model.entity.ArticleEntity
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Single

interface ArticleListRepositoryContract {

    fun loadArticleItems(feedId: String): Single<List<ArticleEntity>>

    fun continueLoadArticleItems(feedId: String): Single<List<ArticleEntity>>

    fun continueLoadAllArticleItems(): Single<List<ArticleEntity>>

    fun loadAllArticleItems(): Single<List<ArticleEntity>>

    fun loadAllArticleItemsV2(): Single<List<ArticleResponseModel>>

    fun continueLoadAllArticleItemsV2(): Single<List<ArticleResponseModel>>
}