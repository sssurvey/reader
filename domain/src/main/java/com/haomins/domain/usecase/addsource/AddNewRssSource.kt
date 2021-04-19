package com.haomins.domain.usecase.addsource

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.model.AddSourceResponseModel
import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class AddNewRssSource @Inject constructor(
    private val addSourceRepositoryContract: AddSourceRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<AddNewRssSource.Companion.Param, AddSourceResponseModel>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Param?): Single<AddSourceResponseModel> {
        if (params == null) throw ParamsShouldNotBeNullException()
        else {
            return addSourceRepositoryContract
                .addSource(params.source)
        }
    }

    companion object {

        data class Param(
            val source: String
        )

        fun forAddNewRssSource(source: String): Param {
            return Param(source)
        }

    }

}