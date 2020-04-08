package com.haomins.reader.di

import android.app.Application
import com.haomins.reader.ReaderApplication
import com.haomins.reader.di.modules.*
import com.haomins.www.data.di.DataModule
import com.haomins.www.data.di.PreferenceModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        DataModule::class,
        FragmentModule::class,
        ViewModelModule::class,
        PreferenceModule::class
    ]
)
interface AppComponent {

    fun inject(readerApplication: ReaderApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}