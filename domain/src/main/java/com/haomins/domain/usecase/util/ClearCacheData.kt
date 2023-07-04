package com.haomins.domain.usecase.util

import com.haomins.domain.repositories.local.AppCacheSizeRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class ClearCacheData @Inject constructor(
    private val appCacheSizeRepository: AppCacheSizeRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
): CompletableUseCase<Unit>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseCompletable(params: Unit?): Completable {
        return appCacheSizeRepository.clearCache()
    }

}