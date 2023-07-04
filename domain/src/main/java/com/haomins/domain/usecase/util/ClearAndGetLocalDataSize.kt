package com.haomins.domain.usecase.util

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.HighLevelUseCase
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

@HighLevelUseCase(
    uses = [ClearLocalData::class, GetLocalDataSize::class]
)
class ClearAndGetLocalDataSize @Inject constructor(
    private val clearLocalData: ClearLocalData,
    private val getLocalDataSize: GetLocalDataSize,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, Long>(
    executionScheduler, postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<Long> {
        return clearLocalData
            .buildUseCaseCompletable(Unit)
            .andThen(getLocalDataSize.buildUseCaseSingle(Unit))
    }

}