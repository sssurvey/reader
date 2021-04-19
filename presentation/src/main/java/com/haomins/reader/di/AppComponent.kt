package com.haomins.reader.di

import android.app.Application
import com.haomins.reader.ReaderApplication
import com.haomins.reader.di.modules.PresentationModule
//import com.haomins.reader.di.modules.SubcomponentModule
import com.haomins.reader.viewModels.di.ViewModelComponent
//import com.haomins.reader.viewModels.di.ViewModelComponent
//import com.haomins.www.model.di.DataComponent
import com.haomins.www.model.di.module.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        PresentationModule::class,
        DataModule::class,
        PreferenceModule::class,
        RepositoryModule::class,
        RxModule::class,
        SchedulerModule::class
    ]
)
interface AppComponent {

    fun inject(readerApplication: ReaderApplication)

    fun viewModelComponent(): ViewModelComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

}