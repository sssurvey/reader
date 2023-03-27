package com.haomins.domain.repositories.remote

import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Single

interface ArticleListRemoteRepository {

    fun loadAllArticleItemsFromRemote(continueId: String): Single<List<Pair<String, ArticleResponseModel>>>

    fun loadAllArticleItemsFromRemoteWithFeed(
        feedId: String,
        continueId: String
    ): Single<List<Pair<String, ArticleResponseModel>>>

}