package com.haomins.www.data.strategies

import io.reactivex.Observable
import io.reactivex.Single

interface RxSchedulingStrategy {

    fun <T> Observable<T>.useDefaultSchedulingPolicy(): Observable<T>

    fun <T> Single<T>.useDefaultSchedulingPolicy(): Single<T>

    fun <T> Single<T>.useIoThreadsOnly(): Single<T>

}
