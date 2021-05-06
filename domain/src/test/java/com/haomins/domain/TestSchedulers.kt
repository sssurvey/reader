package com.haomins.domain

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

object TestSchedulers {

    private val executionScheduler by lazy {
        object : ExecutionScheduler {

            private val testScheduler by lazy {
                TestScheduler()
            }

            override val scheduler: Scheduler
                get() = testScheduler

        }
    }

    private val postExecutionScheduler by lazy {
        object : PostExecutionScheduler {

            private val testScheduler by lazy {
                TestScheduler()
            }

            override val scheduler: Scheduler
                get() = testScheduler

        }
    }

    internal fun executionScheduler(): ExecutionScheduler {
        return executionScheduler
    }

    internal fun postExecutionScheduler(): PostExecutionScheduler {
        return postExecutionScheduler
    }

}