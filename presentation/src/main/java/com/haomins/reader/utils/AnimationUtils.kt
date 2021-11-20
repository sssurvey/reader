package com.haomins.reader.utils

import android.app.Activity
import android.util.Log
import com.haomins.reader.R
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private const val TAG = "AnimationUtils"

fun Activity.slideInAnimation() {
    this.overridePendingTransition(R.anim.start_slide_in_left, R.anim.start_slide_out_left)
}

fun Activity.slideOutAnimation() {
    this.overridePendingTransition(R.anim.start_slide_in_right, R.anim.start_slide_out_right)
}

fun Activity.delayedUiOperation(
    seconds: Long,
    doAfterDelay: () -> Unit,
    doOnError: (Throwable) -> Unit = { Log.e(TAG, "onError :: ${it.printStackTrace()}") },
    executionScheduler: Scheduler = Schedulers.io(),
    observationScheduler: Scheduler = AndroidSchedulers.mainThread()
) {
    Single
        .timer(seconds, TimeUnit.SECONDS, executionScheduler)
        .observeOn(observationScheduler)
        .subscribe(object : DisposableSingleObserver<Long>() {
            override fun onSuccess(t: Long) {
                doAfterDelay.invoke()
            }

            override fun onError(e: Throwable) {
                doOnError.invoke(e)
            }
        })
}