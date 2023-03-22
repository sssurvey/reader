package com.haomins.domain.usecase.login

import com.haomins.domain.repositories.LoginRemoteRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class SignUp @Inject constructor(
    private val loginRemoteRepository: LoginRemoteRepository,
    postExecutionScheduler: PostExecutionScheduler,
    executionScheduler: ExecutionScheduler
) : SingleUseCase<Unit, String>(
    postExecutionScheduler = postExecutionScheduler,
    executionScheduler = executionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<String> {
        return Single.fromCallable {
            loginRemoteRepository.getSignUpUrlString()
        }
    }

}