package com.haomins.domain.usecase

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver

abstract class SingleUseCase<in Params, T> constructor(
        private val executionScheduler: ExecutionScheduler,
        private val postExecutionScheduler: PostExecutionScheduler
) {

    private val compositeDisposable = CompositeDisposable()

    internal abstract fun buildUseCaseSingle(params: Params?): Single<T>

    open fun execute(observer: DisposableSingleObserver<T>, params: Params? = null) {
        this
                .buildUseCaseSingle(params)
                .subscribeOn(executionScheduler.scheduler)
                .observeOn(postExecutionScheduler.scheduler)
                .subscribeWith(observer)
                .also {
                    compositeDisposable.add(it)
                }
    }

    fun dispose() {
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }

    fun clear() {
        if (compositeDisposable.size() > 0) compositeDisposable.clear()
    }

    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

}