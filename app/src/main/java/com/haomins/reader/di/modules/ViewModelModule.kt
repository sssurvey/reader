package com.haomins.reader.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.haomins.reader.viewModels.CustomViewModelFactory
import com.haomins.reader.viewModels.MainActivityViewModel
import com.haomins.reader.di.ViewModelKey
import com.haomins.reader.viewModels.SourceTitleListViewModel
import com.haomins.reader.viewModels.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SourceTitleListViewModel::class)
    abstract fun bindSourceTitleListViewModel(sourceTitleListViewModel: SourceTitleListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(customViewModelFactory: CustomViewModelFactory): ViewModelProvider.Factory
}