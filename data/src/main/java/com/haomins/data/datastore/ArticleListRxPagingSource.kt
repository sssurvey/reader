package com.haomins.data.datastore

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.haomins.data.datastore.remote.ArticleListRemoteDataStore
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.ModelToEntityMapper
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject

//TODO: 143 double check later
class ArticleListRxPagingSource @Inject constructor(
    private val articleListRemoteDataStore: ArticleListRemoteDataStore,
    private val mapper: ModelToEntityMapper
) : RxPagingSource<String, ArticleEntity>() {

    private val continueIdToPageMap = mutableMapOf(INITIAL_CONTINUE_ID to 0)
    private val pageToContinueIdMap = mutableMapOf(0 to INITIAL_CONTINUE_ID)
    private var pageCounter = 0

    override fun getRefreshKey(state: PagingState<String, ArticleEntity>): String? {
        return null
    }

    override fun loadSingle(params: LoadParams<String>): Single<LoadResult<String, ArticleEntity>> {
        return articleListRemoteDataStore
            .loadAllArticleItemsFromRemote(pageToContinueIdMap[pageCounter]!!)
            .map {
                pageCounter += 1

                val continueId = it.last().first
                val content = it.map { it.second }

                // keep track of page
                run {
                    continueIdToPageMap[continueId] = pageCounter
                    pageToContinueIdMap[pageCounter] = continueId
                }

                val prevPage = continueIdToPageMap[params.key]?.let {
                    pageToContinueIdMap[it - 1]
                }
                val nextPage = pageToContinueIdMap[pageCounter]

                LoadResult.Page(
                    data = content.map { mapper.toArticleEntity(it) },
                    prevKey = prevPage,
                    nextKey = nextPage
                )
            }
    }

    companion object {
        private const val INITIAL_CONTINUE_ID = TheOldReaderService.EMPTY
    }
}