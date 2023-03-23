package com.haomins.domain.usecase.article

import com.haomins.domain.common.ModelToEntityMapper
import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.domain.usecase.article.local.LoadAllArticlesByFeedFromLocal
import com.haomins.domain.usecase.article.local.SaveAllArticlesToLocal
import com.haomins.domain.usecase.article.remote.LoadAllArticlesByFeedFromRemote
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject

class LoadAllArticlesByFeedAndSaveToLocal @Inject constructor(
    private val loadAllArticlesByFeedFromRemote: LoadAllArticlesByFeedFromRemote,
    private val saveAllArticlesToLocal: SaveAllArticlesToLocal,
    private val loadAllArticlesByFeedFromLocal: LoadAllArticlesByFeedFromLocal,
    private val modelToEntityMapper: ModelToEntityMapper,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<LoadAllArticlesByFeedAndSaveToLocal.Companion.Params, List<ArticleEntity>>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Params?): Single<List<ArticleEntity>> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return loadAllArticlesByFeedFromRemote
            .buildUseCaseSingle(
                LoadAllArticlesByFeedFromRemote.forLoadAllArticlesByFeedFromRemote(params.feedId)
            )
            .map { articleResponses ->
                articleResponses.map {
                    modelToEntityMapper.toArticleEntity(it)
                }
            }
            .flatMapCompletable {
                saveAllArticlesToLocal
                    .buildUseCaseCompletable(
                        SaveAllArticlesToLocal.forSaveAllArticlesToLocal(it)
                    )
            }.andThen(
                loadAllArticlesByFeedFromLocal
                    .buildUseCaseSingle(
                        LoadAllArticlesByFeedFromLocal.forLoadAllArticlesByFeedFromLocal(params.feedId)
                    )
            ).onErrorResumeNext {
                loadAllArticlesByFeedFromLocal
                    .buildUseCaseSingle(
                        LoadAllArticlesByFeedFromLocal.forLoadAllArticlesByFeedFromLocal(params.feedId)
                    )
            }
    }

    companion object {

        data class Params(
            val feedId: String
        )

        fun forLoadAllArticlesByFeedAndSaveToLocal(feedId: String): Params {
            return Params(
                feedId
            )
        }
    }
}