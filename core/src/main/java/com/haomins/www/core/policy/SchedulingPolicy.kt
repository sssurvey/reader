package com.haomins.www.core.policy

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SchedulingPolicy @Inject constructor() : RxSchedulingPolicy {

    override fun <T> Observable<T>.defaultSchedulingPolicy(): Observable<T> {
        return this
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun <T> Single<T>.defaultSchedulingPolicy(): Single<T> {
        return this
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

}