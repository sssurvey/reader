package com.haomins.domain.usecase.article

import com.haomins.domain.common.ModelToEntityMapper
import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SerializedExecutionOnly
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.domain.usecase.article.local.LoadAllArticlesByFeedFromLocal
import com.haomins.domain.usecase.article.local.SaveAllArticlesToLocal
import com.haomins.domain.usecase.article.remote.ContinueLoadAllArticlesByFeedFromRemote
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject

class ContinueLoadAllArticlesByFeedAndSaveToLocal @Inject constructor(
    private val continueLoadAllArticlesByFeedFromRemote: ContinueLoadAllArticlesByFeedFromRemote,
    private val saveAllArticlesToLocal: SaveAllArticlesToLocal,
    private val loadAllArticlesByFeedFromLocal: LoadAllArticlesByFeedFromLocal,
    private val modelToEntityMapper: ModelToEntityMapper,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<ContinueLoadAllArticlesByFeedAndSaveToLocal.Companion.Params, List<ArticleEntity>>(
    executionScheduler,
    postExecutionScheduler
), SerializedExecutionOnly<List<ArticleEntity>> {

    override var isExecuting = false

    override fun buildUseCaseSingle(params: Params?): Single<List<ArticleEntity>> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return continueLoadAllArticlesByFeedFromRemote
            .buildUseCaseSingle(
                ContinueLoadAllArticlesByFeedFromRemote.forContinueLoadAllArticlesByFeedFromRemote(
                    params.feedId
                )
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
            }.checkForExecuting()
    }

    companion object {

        data class Params(
            val feedId: String
        )

        fun forContinueLoadAllArticlesByFeedAndSaveToLocal(feedId: String): Params {
            return Params(
                feedId
            )
        }
    }
}