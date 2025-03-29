package com.haomins.domain.usecase.logging

import com.haomins.domain.repositories.local.LoggingLocalRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.HighLevelUseCase
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.domain.usecase.disclosure.LoadDisclosureContent
import com.haomins.model.LogReport
import io.reactivex.Single
import javax.inject.Inject

@HighLevelUseCase(uses = [LoadDisclosureContent::class])
class GetLogFilesThenSendEmail @Inject constructor(
    private val loggingLocalRepository: LoggingLocalRepository,
    private val loadDisclosureContent: LoadDisclosureContent,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, LogReport>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<LogReport> {
        return Single
            .fromCallable { loggingLocalRepository.getLogFile() }
            .zipWith(
                loadDisclosureContent.buildUseCaseSingle(Unit)
            ) { file, disclose ->
                LogReport(email = disclose.contactEmail, file = file)
            }
    }
}