package com.haomins.www.core.util

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal fun <T> Observable<T>.defaultSchedulingPolicy(): Observable<T> {
    return this
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
}

internal fun <T> Single<T>.defaultSchedulingPolicy(): Single<T> {
    return this
        .observeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
}