package com.haomins.data.datastore

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.repositories.ArticleListPagingRepository
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Flowable
import javax.inject.Inject

//TODO: 143 double check later
class ArticleListPagingDataStore @Inject constructor(
    private val articleListRxPagingSource: ArticleListRxPagingSource
): ArticleListPagingRepository {

    override fun getArticleListStream(): Flowable<PagingData<ArticleEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = TheOldReaderService.DEFAULT_LOAD_SOURCE_ARTICLE_COUNT,
                enablePlaceholders = true,
                prefetchDistance = TheOldReaderService.DEFAULT_LOAD_SOURCE_ARTICLE_COUNT,
                initialLoadSize = TheOldReaderService.DEFAULT_LOAD_SOURCE_ARTICLE_COUNT
            ),
            pagingSourceFactory = { articleListRxPagingSource }
        ).flowable
    }

}