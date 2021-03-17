package com.haomins.domain.scheduler

import io.reactivex.Scheduler

interface PostExecutionScheduler {
    val scheduler: Scheduler
}