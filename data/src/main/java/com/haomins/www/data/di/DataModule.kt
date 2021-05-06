package com.haomins.www.data.di

import com.haomins.www.data.di.module.*
import dagger.Module

@Module(includes = [
    ConnectionModule::class,
    PreferenceModule::class,
    RepositoryModule::class,
    RxModule::class,
    SchedulerModule::class
])
interface DataModule