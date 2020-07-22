package com.haomins.www.core.di

import android.app.Application
import android.content.SharedPreferences
import com.haomins.www.core.di.module.DataModule
import com.haomins.www.core.di.module.PreferenceModule
import com.haomins.www.core.di.module.RxModule
import com.haomins.www.core.strategies.RxSchedulingStrategy
import com.haomins.www.core.service.TheOldReaderService
import dagger.BindsInstance
import dagger.Component

@Component(modules = [
    DataModule::class,
    PreferenceModule::class,
    RxModule::class
])
interface CoreComponent {

    fun provideSharedPreferences() : SharedPreferences

    fun provideTheOldReaderService() : TheOldReaderService

    fun provideRxSchedulingPolicy() : RxSchedulingStrategy

    fun provideApplication() : Application

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): CoreComponent

    }

}