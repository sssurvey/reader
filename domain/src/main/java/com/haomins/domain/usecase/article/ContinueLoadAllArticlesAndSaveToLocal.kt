package com.haomins.domain.usecase.article

import com.haomins.domain.common.ModelToEntityMapper
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SerializedExecutionOnly
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.domain.usecase.article.local.LoadAllArticlesFromLocal
import com.haomins.domain.usecase.article.local.SaveAllArticlesToLocal
import com.haomins.domain.usecase.article.remote.ContinueLoadAllArticlesFromRemote
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject

class ContinueLoadAllArticlesAndSaveToLocal @Inject constructor(
    private val continueLoadAllArticlesFromRemote: ContinueLoadAllArticlesFromRemote,
    private val saveAllArticlesToLocal: SaveAllArticlesToLocal,
    private val loadAllArticlesFromLocal: LoadAllArticlesFromLocal,
    private val modelToEntityMapper: ModelToEntityMapper,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, List<ArticleEntity>>(
    executionScheduler,
    postExecutionScheduler
), SerializedExecutionOnly<List<ArticleEntity>> {

    override var isExecuting = false

    override fun buildUseCaseSingle(params: Unit?): Single<List<ArticleEntity>> {
        return continueLoadAllArticlesFromRemote
            .buildUseCaseSingle(Unit)
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
                loadAllArticlesFromLocal.buildUseCaseSingle(Unit)
            ).onErrorResumeNext {
                loadAllArticlesFromLocal.buildUseCaseSingle(Unit)
            }
            .checkForExecuting()
    }

}