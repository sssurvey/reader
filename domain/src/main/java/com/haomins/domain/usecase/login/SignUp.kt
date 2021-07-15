package com.haomins.domain.usecase.login

import com.haomins.domain.repositories.LoginRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class SignUp @Inject constructor(
        private val loginRepositoryContract: LoginRepositoryContract,
        postExecutionScheduler: PostExecutionScheduler,
        executionScheduler: ExecutionScheduler
) : SingleUseCase<Unit, String>(
        postExecutionScheduler = postExecutionScheduler,
        executionScheduler = executionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<String> {
        return Single.fromCallable {
            loginRepositoryContract.getSignUpUrlString()
        }
    }

}