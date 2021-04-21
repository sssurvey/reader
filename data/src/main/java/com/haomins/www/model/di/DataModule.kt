package com.haomins.www.model.di

import com.haomins.www.model.di.module.*
import dagger.Module

@Module(includes = [
    ConnectionModule::class,
    PreferenceModule::class,
    RepositoryModule::class,
    RxModule::class,
    SchedulerModule::class
])
interface DataModule