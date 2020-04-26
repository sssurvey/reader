package com.haomins.reader.di

import com.haomins.reader.ReaderApplication
import com.haomins.reader.di.modules.ActivityModule
import com.haomins.reader.di.modules.FragmentModule
import com.haomins.reader.di.modules.ViewModelModule
import com.haomins.www.core.di.CoreComponent
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        CoreComponent::class
    ],
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(readerApplication: ReaderApplication)

    @Component.Builder
    interface Builder {

        fun setCoreComponent(coreComponent: CoreComponent): Builder

        fun build(): AppComponent

    }

}