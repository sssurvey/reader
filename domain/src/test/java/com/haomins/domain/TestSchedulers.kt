package com.haomins.domain

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

object TestSchedulers {

    private val executionScheduler by lazy {
        object : ExecutionScheduler {
            override val scheduler: Scheduler
                get() = TestScheduler()
        }
    }

    private val postExecutionScheduler by lazy {
        object : PostExecutionScheduler {
            override val scheduler: Scheduler
                get() = TestScheduler()
        }
    }

    internal fun executionScheduler(): ExecutionScheduler {
        return executionScheduler
    }

    internal fun postExecutionScheduler(): PostExecutionScheduler {
        return postExecutionScheduler
    }

}