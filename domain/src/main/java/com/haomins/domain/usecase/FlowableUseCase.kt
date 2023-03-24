package com.haomins.domain.usecase

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subscribers.DisposableSubscriber

abstract class FlowableUseCase<in Params, T> constructor(
    private val executionScheduler: ExecutionScheduler,
    private val postExecutionScheduler: PostExecutionScheduler
) {

    private val compositeDisposable = CompositeDisposable()

    internal abstract fun buildUseCaseFlowable(params: Params?): Flowable<T>

    open fun execute(flowableSubscriber: DisposableSubscriber<T>, params: Params? = null) {
        this
            .buildUseCaseFlowable(params)
            .subscribeOn(executionScheduler.scheduler)
            .observeOn(postExecutionScheduler.scheduler)
            .subscribeWith(flowableSubscriber)
            .also { addDisposable(it) }
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