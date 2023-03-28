package com.haomins.data.datastore

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.haomins.data.datastore.local.ArticleListLocalDataStore
import com.haomins.data.datastore.remote.ArticleListRemoteDataStore
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.ModelToEntityMapper
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticleListRemoteMediator @Inject constructor(
    private val articleListRemoteDataStore: ArticleListRemoteDataStore,
    private val articleListLocalDataStore: ArticleListLocalDataStore,
    private val mapper: ModelToEntityMapper
) : RxRemoteMediator<Int, ArticleEntity>() {

    private val continueIdToPageMap = mutableMapOf(INITIAL_CONTINUE_ID to 0)
    private val pageToContinueIdMap = mutableMapOf(0 to INITIAL_CONTINUE_ID)
    private var pageCounter = 0

    var feedId = ""

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): Single<MediatorResult> {

        val remoteKey: Single<Int>

        when (loadType) {
            // reset
            LoadType.REFRESH -> {
                pageCounter = 0
                remoteKey = Single.just(pageToContinueIdMap[0])
            }
            LoadType.APPEND -> {
                remoteKey = Single.just(pageToContinueIdMap[pageCounter])
            }
            // Ignore
            LoadType.PREPEND -> {
                return Single.just(MediatorResult.Success(true))
            }
        }

        return remoteKey
            .flatMap {
                val continueId = if (it <= 0) TheOldReaderService.EMPTY else it.toString()
                if (feedId.isEmpty()) {
                    articleListRemoteDataStore
                        .loadAllArticleItemsFromRemote(continueId = continueId)
                } else {
                    articleListRemoteDataStore
                        .loadAllArticleItemsFromRemoteWithFeed(feedId, continueId = continueId)
                }

            }.flatMap { list ->

                // keep track of page in the case of APPEND
                if (loadType == LoadType.APPEND) {
                    pageCounter += 1

                    val continueId = list.last().first.toInt()

                    run {
                        continueIdToPageMap[continueId] = pageCounter
                        pageToContinueIdMap[pageCounter] = continueId
                    }
                }

                val content = list.map { it.second }

                articleListLocalDataStore.saveAllArticles(
                    content.map { mapper.toArticleEntity(it) }
                ).andThen(Single.just(list.last().first.isBlank()))

            }
            .flatMap {
                Single.just(MediatorResult.Success(it) as MediatorResult)
            }
            .onErrorResumeNext {
                when (it) {
                    is IOException,
                    is HttpException -> Single.just(MediatorResult.Error(it) as MediatorResult)
                    else -> Single.error(it)
                }
            }
    }

    companion object {
        private const val INITIAL_CONTINUE_ID = -1
    }
}