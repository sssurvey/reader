package com.haomins.www.model.di

import android.app.Application
import android.content.SharedPreferences
import com.haomins.www.model.di.module.*
import com.haomins.www.model.service.TheOldReaderService
import com.haomins.www.model.strategies.RxSchedulingStrategy
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    DataModule::class,
    PreferenceModule::class,
    RepositoryModule::class,
    RxModule::class,
    SchedulerModule::class
])
interface DataComponent {

    fun provideSharedPreferences() : SharedPreferences

    fun provideTheOldReaderService() : TheOldReaderService

    fun provideRxSchedulingPolicy() : RxSchedulingStrategy

    fun provideApplication() : Application

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): DataComponent

    }

}