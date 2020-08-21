package com.haomins.www.model.strategies

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DefaultSchedulingStrategy @Inject constructor() : RxSchedulingStrategy {

    override fun <T> Observable<T>.useDefaultSchedulingPolicy(): Observable<T> {
        return this
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun <T> Single<T>.useDefaultSchedulingPolicy(): Single<T> {
        return this
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

}