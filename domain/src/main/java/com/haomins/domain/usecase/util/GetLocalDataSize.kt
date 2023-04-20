package com.haomins.domain.usecase.util

import com.haomins.domain.repositories.local.AppCacheSizeRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GetLocalDataSize @Inject constructor(
    private val appCacheSizeRepository: AppCacheSizeRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
): SingleUseCase<Unit, Long>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<Long> {
        return appCacheSizeRepository.getCurrentCacheSize()
    }

}