package com.haomins.domain.usecase.login

import com.haomins.domain.repositories.remote.LoginRemoteRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class ForgetPassword @Inject constructor(
    private val loginRemoteRepository: LoginRemoteRepository,
    postExecutionScheduler: PostExecutionScheduler,
    executionScheduler: ExecutionScheduler
) : SingleUseCase<Unit, String>(
    postExecutionScheduler = postExecutionScheduler,
    executionScheduler = executionScheduler
) {

    /**
     * Return a string representation of url linked to The Old Reader forget user password page.
     *
     * @param params Unit
     * @return a {@code Single<String>} contains {@code String} that represent the url that linked
     * to the forget password page of The Old Reader website.
     */
    override fun buildUseCaseSingle(params: Unit?): Single<String> {
        return Single.fromCallable {
            loginRemoteRepository.getForgetPasswordUrlString()
        }
    }

}