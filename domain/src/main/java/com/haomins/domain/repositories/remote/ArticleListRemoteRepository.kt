package com.haomins.domain.repositories.remote

import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Single

interface ArticleListRemoteRepository {

    /**
     * Load article items from remote source.
     *
     * This API call will return a [Single] with [Pair], [Pair.first] being [continueId]
     * [Pair.second] being [List] of [ArticleResponseModel]
     *
     * @param continueId Empty string for getting the first page, new [continueId] will be returned
     * via each request.
     *
     * @return [Single] that contains a [Pair], with [Pair.first] being the [continueId]
     * for next page, and [Pair.second] as the content of the [ArticleResponseModel].
     */
    fun loadAllArticleItemsFromRemote(continueId: String): Single<Pair<String, List<ArticleResponseModel>>>

    /**
     * Load article items from remote source limited by feed.
     *
     * @see loadAllArticleItemsFromRemote the idea of these two API are similar.
     *
     * @param feedId string so that we can request article from specific feed source.
     * @param continueId Empty string for getting the first page, new [continueId] will be returned
     * via each request.
     *
     * @return [Single] that contains a [Pair], with [Pair.first] being the [continueId]
     * for next page, and [Pair.second] as the content of the [ArticleResponseModel].
     */
    fun loadAllArticleItemsFromRemoteWithFeed(
        feedId: String,
        continueId: String
    ): Single<Pair<String, List<ArticleResponseModel>>>

}