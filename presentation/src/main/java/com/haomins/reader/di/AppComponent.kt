package com.haomins.reader.di

import com.haomins.reader.ReaderApplication
import com.haomins.reader.di.modules.SubcomponentModule
import com.haomins.reader.viewModels.di.ViewModelComponent
import com.haomins.www.core.di.CoreComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [CoreComponent::class],
    modules = [SubcomponentModule::class]
)
interface AppComponent {

    fun inject(readerApplication: ReaderApplication)

    fun viewModelComponent(): ViewModelComponent.Builder

    @Component.Builder
    interface Builder {

        fun setCoreComponent(coreComponent: CoreComponent): Builder

        fun build(): AppComponent

    }

}