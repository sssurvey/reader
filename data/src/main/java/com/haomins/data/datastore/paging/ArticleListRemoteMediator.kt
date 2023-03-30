package com.haomins.data.datastore.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.haomins.data.datastore.local.ArticleListLocalDataStore
import com.haomins.data.datastore.remote.ArticleListRemoteDataStore
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.ModelToEntityMapper
import com.haomins.domain.exception.SourceEmptyException
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

    private var continueId = TheOldReaderService.EMPTY

    var feedId = ""

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): Single<MediatorResult> {

        val remoteKey: Single<String>

        when (loadType) {
            // reset
            LoadType.REFRESH -> {
                continueId = TheOldReaderService.EMPTY
                remoteKey = Single.just(continueId)
            }
            LoadType.APPEND -> {
                remoteKey = Single.just(continueId)
            }
            // Ignore
            LoadType.PREPEND -> {
                return Single.just(MediatorResult.Success(true))
            }
        }

        return remoteKey
            .flatMap {
                if (feedId.isEmpty()) {
                    articleListRemoteDataStore
                        .loadAllArticleItemsFromRemote(continueId = it)
                } else {
                    articleListRemoteDataStore
                        .loadAllArticleItemsFromRemoteWithFeed(feedId, continueId = it)
                }
            }.flatMap { pair ->

                val (newContinueID, content) = pair

                // business logic
                run {
                    content.ifEmpty { throw SourceEmptyException() }
                }

                // keep track of continue id for next page
                continueId = newContinueID

                // cache articles to DB
                articleListLocalDataStore.saveAllArticles(
                    content.map { mapper.toArticleEntity(it) }
                ).andThen(
                    Single.just(newContinueID.isEmpty())
                )

            }
            .flatMap {
                Single
                    .just(
                        MediatorResult.Success(endOfPaginationReached = it) as MediatorResult
                    )
            }
            .onErrorResumeNext {
                when (it) {
                    is IOException,
                    is HttpException -> Single.just(MediatorResult.Error(it) as MediatorResult)
                    else -> Single.error(it)
                }
            }
    }

}