package com.haomins.domain.repositories.remote

import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Single

interface ArticleListRemoteRepository {

    /**
     * Load article items from remote source.
     *
     * Since the implementation of paging from remote is based on the [continueId], therefore
     * if [loadAllArticleItemsFromRemote] is called with empty [continueId], then first page is of
     * items are requested, the return [Single] will contain a list of [Pair] where [Pair.first] is
     * the [continueId] we can use to call the next page, and [Pair.second] is the content of article.
     *
     * @param continueId Empty string for getting the first page, new [continueId] will be returned
     * via each request.
     *
     * @return [Single] that contains a [List] of [Pair], with [Pair.first] being the [continueId]
     * for next page, and [Pair.second] as the content of the [ArticleResponseModel].
     */
    fun loadAllArticleItemsFromRemote(continueId: String): Single<List<Pair<String, ArticleResponseModel>>>

    /**
     * Load article items from remote source limited by feed.
     *
     * @see loadAllArticleItemsFromRemote the idea of these two API are similar.
     *
     * @param feedId string so that we can request article from specific feed source.
     * @param continueId Empty string for getting the first page, new [continueId] will be returned
     * via each request.
     *
     * @return [Single] that contains a [List] of [Pair], with [Pair.first] being the [continueId]
     * for next page, and [Pair.second] as the content of the [ArticleResponseModel].
     */
    fun loadAllArticleItemsFromRemoteWithFeed(
        feedId: String,
        continueId: String
    ): Single<List<Pair<String, ArticleResponseModel>>>

}