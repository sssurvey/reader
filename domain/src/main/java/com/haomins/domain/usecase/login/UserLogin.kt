package com.haomins.domain.usecase.login

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.model.UserAuthResponseModel
import com.haomins.domain.repositories.LoginRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class UserLogin @Inject constructor(
        private val loginRepositoryContract: LoginRepositoryContract,
        executionScheduler: ExecutionScheduler,
        postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<UserLogin.Companion.Param, UserAuthResponseModel>(
        executionScheduler = executionScheduler,
        postExecutionScheduler = postExecutionScheduler
) {

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

        fun forUserLogin(userName: String, userPassword: String): Param {
            return Param(
                    userName = userName,
                    userPassword = userPassword
            )
        }

    }

}