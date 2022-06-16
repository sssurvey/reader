package com.haomins.domain.usecase

import com.haomins.domain.exception.DuplicateRequestException
import io.reactivex.Single

interface SerializedExecutionOnly<T> {

    var isExecuting: Boolean

    fun Single<T>.checkForExecuting(): Single<T> {
        return if (isExecuting) {
            Single.error(DuplicateRequestException())
        } else {
            isExecuting = true
            this.doAfterTerminate { isExecuting = false }
        }
    }
}