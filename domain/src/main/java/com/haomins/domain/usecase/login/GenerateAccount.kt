package com.haomins.domain.usecase.login

import com.haomins.domain.repositories.LoginRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class GenerateAccount @Inject constructor(
        private val loginRepositoryContract: LoginRepositoryContract,
        postExecutionScheduler: PostExecutionScheduler,
        executionScheduler: ExecutionScheduler
): SingleUseCase<Unit, String>(
        postExecutionScheduler = postExecutionScheduler,
        executionScheduler = executionScheduler
) {

    /**
     * Return a string representation of url linked to The Old Reader settings page. Since
     * The Old Reader API does not support user with 3rd party login, i.e: Facebook or Google. The
     * user will have to use the settings page on the website to generate their username and password
     * in order to use them to login Reader app.
     *
     * @param params Unit
     * @return a {@code Single<String>} contains {@code String} that represent the url that linked
     * to the settings page of The Old Reader website.
     */
    override fun buildUseCaseSingle(params: Unit?): Single<String> {
        return Single.fromCallable {
            loginRepositoryContract.getGenerateAccountUrlString()
        }
    }

}