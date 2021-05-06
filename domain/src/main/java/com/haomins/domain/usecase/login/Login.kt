package com.haomins.domain.usecase.login

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.model.UserAuthResponseModel
import com.haomins.domain.repositories.LoginRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class Login @Inject constructor(
        private val loginRepositoryContract: LoginRepositoryContract,
        executionScheduler: ExecutionScheduler,
        postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Login.Companion.Param, UserAuthResponseModel>(
        executionScheduler = executionScheduler,
        postExecutionScheduler = postExecutionScheduler
) {

    /**
     * Send a request by using the account information provided. Up on success, auth data is returned
     * from The Old Reader. With the auth key further request can be made to The Old Reader API.
     *
     * @param params takes {@code String} userName and {@code String} userPassword
     * @return a {@code Single<UserAuthResponseModel>} contains {@code auth: String} with the auth
     * value.
     *
     * @throws ParamsShouldNotBeNullException param must be non-null in this use case
     */
    override fun buildUseCaseSingle(params: Param?): Single<UserAuthResponseModel> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return loginRepositoryContract
                .login(
                        params.userName,
                        params.userPassword
                )
    }

    companion object {

        data class Param(
                val userName: String,
                val userPassword: String
        )

        /**
         * Create {@code Param} with {@code userName: String} and {@code userPassword: String}, for
         * the use case to execute.
         *
         * @param userName string representation of The Old Reader username
         * @param userPassword string representation of The Old Reader password
         */
        fun forUserLogin(userName: String, userPassword: String): Param {
            return Param(
                    userName = userName,
                    userPassword = userPassword
            )
        }

    }

}