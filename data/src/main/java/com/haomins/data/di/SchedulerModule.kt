package com.haomins.data.di

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SchedulerModule {

    @Provides
    @Singleton
    fun provideExecutionScheduler(): ExecutionScheduler {
        return object : ExecutionScheduler {
            override val scheduler: Scheduler
                get() = Schedulers.io()
        }
    }

    @Provides
    @Singleton
    fun providePostExecutionScheduler(): PostExecutionScheduler {
        return object : PostExecutionScheduler {
            override val scheduler: Scheduler
                get() = AndroidSchedulers.mainThread()
        }
    }

}