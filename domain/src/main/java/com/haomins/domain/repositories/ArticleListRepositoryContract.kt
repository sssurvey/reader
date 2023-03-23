package com.haomins.domain.repositories

import com.haomins.model.entity.ArticleEntity
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Single

interface ArticleListRepositoryContract {

    fun loadArticleItems(feedId: String): Single<List<ArticleEntity>>

    fun continueLoadArticleItems(feedId: String): Single<List<ArticleEntity>>

    fun loadAllArticleItems(): Single<List<ArticleResponseModel>>

    fun continueLoadAllArticleItems(): Single<List<ArticleResponseModel>>

    fun continueLoadAllArticleItemsV2(feedId: String): Single<List<ArticleResponseModel>>

    fun loadArticleAllItemsV2(feedId: String): Single<List<ArticleResponseModel>>

}