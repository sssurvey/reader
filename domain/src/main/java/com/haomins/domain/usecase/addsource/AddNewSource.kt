package com.haomins.domain.usecase.addsource

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.model.responses.AddSourceResponseModel
import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.domain.usecase.UseCaseConstants.MEDIUM_RSS_FEED_BASE
import io.reactivex.Single
import javax.inject.Inject

class AddNewSource @Inject constructor(
    private val addSourceRepositoryContract: AddSourceRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<AddNewSource.Companion.Param, AddSourceResponseModel>(
    executionScheduler,
    postExecutionScheduler
) {

    /**
     * Send a add new rss source to the sever, up on successful response, it will return the status
     * and the error? message of the response, indicating if the source has been successfully added
     * or not.
     *
     * @param params takes a feed url in string format
     * @return a {@code Single<AddSourceResponseModel>} contains {@code result} with 1 as success,
     * 0 as fail. And a nullable {@code error String} that is null if success, else it will the error
     * message string.
     *
     * @throws ParamsShouldNotBeNullException param must be non-null in this use case
     */
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

        /**
         *  Create {@code Param} with {@code source} in the typical rss format in String:
         *  {@code "rss.example.com"}.
         *
         *  @param source String rss url
         *  @return {@code AddNewRssSource.Param}
         */
        fun forAddNewRssSource(source: String): Param {
            return Param(source)
        }

        /**
         *  Create {code Param} with {@code source} in the Medium source format in String:
         *  {@code "medium.com/feed/${your_source}"}.
         *
         *  @param source String Medium url
         *  @return {@code AddNewRssSource.Param}
         */
        fun forAddingNewMediumSource(source: String): Param {
            return Param(MEDIUM_RSS_FEED_BASE + source)
        }

    }

}