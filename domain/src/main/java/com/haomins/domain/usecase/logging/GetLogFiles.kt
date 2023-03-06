package com.haomins.domain.usecase.logging

import com.haomins.domain.repositories.LoggingRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class GetLogFiles @Inject constructor(
    private val loggingRepositoryContract: LoggingRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, File>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<File> {
        return Single.fromCallable {
            loggingRepositoryContract.getLogFile()
        }
    }
}