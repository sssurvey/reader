package com.haomins.data.datastore

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.haomins.data.datastore.local.ArticleListLocalDataStore
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.repositories.ArticleListPagingRepository
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Flowable
import javax.inject.Inject

class ArticleListPagingDataStore @Inject constructor(
    private val articleListRemoteMediator: ArticleListRemoteMediator,
    private val articleListLocalDataStore: ArticleListLocalDataStore
): ArticleListPagingRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticleListStream(): Flowable<PagingData<ArticleEntity>> {
        articleListRemoteMediator.feedId = ALL_FEED_NO_FILTER
        return Pager(
            config = PagingConfig(
                pageSize = TheOldReaderService.DEFAULT_LOAD_SOURCE_ARTICLE_COUNT,
                enablePlaceholders = true,
                prefetchDistance = TheOldReaderService.DEFAULT_LOAD_SOURCE_ARTICLE_COUNT,
                initialLoadSize = TheOldReaderService.DEFAULT_LOAD_SOURCE_ARTICLE_COUNT
            ),
            remoteMediator = articleListRemoteMediator,
            pagingSourceFactory = { articleListLocalDataStore.loadAllArticles() }
        ).flowable
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticleListStream(feedId: String): Flowable<PagingData<ArticleEntity>> {
        articleListRemoteMediator.feedId = feedId
        return Pager(
            config = PagingConfig(
                pageSize = TheOldReaderService.DEFAULT_LOAD_SOURCE_ARTICLE_COUNT,
                enablePlaceholders = true,
                prefetchDistance = TheOldReaderService.DEFAULT_LOAD_SOURCE_ARTICLE_COUNT,
                initialLoadSize = TheOldReaderService.DEFAULT_LOAD_SOURCE_ARTICLE_COUNT
            ),
            remoteMediator = articleListRemoteMediator,
            pagingSourceFactory = { articleListLocalDataStore.loadAllArticlesFromFeed(feedId) }
        ).flowable
    }

    companion object {
        private const val ALL_FEED_NO_FILTER = ""
    }
}