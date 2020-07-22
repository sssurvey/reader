package com.haomins.www.core

import com.haomins.www.core.strategies.RxSchedulingStrategy
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single

/**
 * Helper class for easier testing with RxJava
 *
 * You may provide the Scheduler you want for the RxJava APIs to run on. When only the subscribeOnScheduler
 * is provided, both observeOnScheduler & subscribeOnScheduler will use the same (ref) scheduler.
 *
 * @param subscribeOnScheduler
 * @param observeOnScheduler default same scheduler as subscribeOnScheduler if no scheduler is provided
 */
class TestSchedulingStrategy(
    val subscribeOnScheduler: Scheduler,
    val observeOnScheduler: Scheduler = subscribeOnScheduler
) : RxSchedulingStrategy {

    override fun <T> Observable<T>.defaultSchedulingPolicy(): Observable<T> {
        return this.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    }

    override fun <T> Single<T>.defaultSchedulingPolicy(): Single<T> {
        return this.subscribeOn(subscribeOnScheduler).observeOn(observeOnScheduler)
    }

}