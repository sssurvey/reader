package com.haomins.domain.usecase

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver

abstract class CompletableUseCase<in Params> constructor(
    private val executionScheduler: ExecutionScheduler,
    private val postExecutionScheduler: PostExecutionScheduler
) {

    private val compositeDisposable = CompositeDisposable()

    internal abstract fun buildUseCaseCompletable(params: Params?): Completable

    open fun execute(observer: DisposableCompletableObserver, params: Params? = null) {
        this
            .buildUseCaseCompletable(params)
            .subscribeOn(executionScheduler.scheduler)
            .observeOn(postExecutionScheduler.scheduler)
            .subscribeWith(observer)
            .also {
                addDisposable(it)
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
