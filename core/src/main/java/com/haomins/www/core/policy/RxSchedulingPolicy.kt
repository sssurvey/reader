package com.haomins.www.core.policy

import io.reactivex.Observable
import io.reactivex.Single

interface RxSchedulingPolicy {

    fun <T> Observable<T>.defaultSchedulingPolicy(): Observable<T>

    fun <T> Single<T>.defaultSchedulingPolicy(): Single<T>

}
