package com.haomins.domain.usecase

import com.haomins.domain.exception.DuplicateRequestException
import io.reactivex.Single

interface SerializedExecutionOnly<T> {

    var isExecuting: Boolean

    fun Single<T>.checkForExecuting(): Single<T> {
        return if (isExecuting) {
            Single.error(DuplicateRequestException())
        } else {
            this.doOnSubscribe { isExecuting = true }
                .doAfterTerminate { isExecuting = false }
        }
    }
}