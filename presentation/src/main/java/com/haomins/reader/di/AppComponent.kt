package com.haomins.reader.di

import com.haomins.reader.ReaderApplication
import com.haomins.reader.di.modules.SubcomponentModule
import com.haomins.reader.viewModels.di.ViewModelComponent
import com.haomins.www.model.di.DataComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [DataComponent::class],
    modules = [SubcomponentModule::class]
)
interface AppComponent {

    fun inject(readerApplication: ReaderApplication)

    fun viewModelComponent(): ViewModelComponent.Builder

    @Component.Builder
    interface Builder {

        fun setDataComponent(dataComponent: DataComponent): Builder

        fun build(): AppComponent

    }

}