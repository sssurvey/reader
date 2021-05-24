package com.haomins.data.di

import com.haomins.data.di.module.*
import dagger.Module

@Module(includes = [
    ConnectionModule::class,
    PreferenceModule::class,
    RepositoryModule::class,
    RxModule::class,
    SchedulerModule::class
])
interface DataModule