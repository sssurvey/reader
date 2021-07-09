package com.haomins.data.strategies

import io.reactivex.Observable
import io.reactivex.Single

@Deprecated(
    message = "we don't use this one anymore",
    replaceWith = ReplaceWith("PostExecutionScheduler and ExecutionScheduler")
)
interface RxSchedulingStrategy {

    fun <T> Observable<T>.useDefaultSchedulingPolicy(): Observable<T>

    fun <T> Single<T>.useDefaultSchedulingPolicy(): Single<T>

    fun <T> Single<T>.useIoThreadsOnly(): Single<T>

}
