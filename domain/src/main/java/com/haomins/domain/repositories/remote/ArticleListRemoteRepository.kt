package com.haomins.domain.repositories.remote

import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Single

interface ArticleListRemoteRepository {

    fun loadAllArticleItems(): Single<List<ArticleResponseModel>>

    fun continueLoadAllArticleItems(): Single<List<ArticleResponseModel>>

    fun continueLoadAllArticleItems(feedId: String): Single<List<ArticleResponseModel>>

    fun loadArticleAllItems(feedId: String): Single<List<ArticleResponseModel>>

}