package com.haomins.domain.scheduler

import io.reactivex.Scheduler

interface ExecutionScheduler {
    val scheduler: Scheduler
}