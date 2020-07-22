package com.haomins.www.core.strategies

import io.reactivex.Observable
import io.reactivex.Single

interface RxSchedulingStrategy {

    fun <T> Observable<T>.defaultSchedulingPolicy(): Observable<T>

    fun <T> Single<T>.defaultSchedulingPolicy(): Single<T>

}
