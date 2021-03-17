package com.haomins.www.model.di

import android.app.Application
import android.content.SharedPreferences
import com.haomins.www.model.di.module.DataModule
import com.haomins.www.model.di.module.PreferenceModule
import com.haomins.www.model.di.module.RxModule
import com.haomins.www.model.strategies.RxSchedulingStrategy
import com.haomins.www.model.service.TheOldReaderService
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