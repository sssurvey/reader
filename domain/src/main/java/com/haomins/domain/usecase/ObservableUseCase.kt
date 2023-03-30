package com.haomins.domain.usecase

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver

abstract class ObservableUseCase<in Params, T> constructor(
    private val executionScheduler: ExecutionScheduler,
    private val postExecutionScheduler: PostExecutionScheduler
) {

    private val compositeDisposable = CompositeDisposable()

    internal abstract fun buildUseCaseObservable(params: Params?): Observable<T>

    open fun execute(observer: DisposableObserver<T>, params: Params? = null) {
        this
            .buildUseCaseObservable(params)
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