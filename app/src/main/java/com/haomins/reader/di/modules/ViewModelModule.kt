package com.haomins.reader.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.haomins.reader.MyViewModelFactory
import com.haomins.reader.activities.main.MainActivityViewModel
import com.haomins.reader.di.ViewModelKey
import com.haomins.reader.fragments.list.SourceTitleListViewModel
import com.haomins.reader.fragments.login.LoginViewModel
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
    abstract fun bindViewModelFactory(myViewModelFactory: MyViewModelFactory): ViewModelProvider.Factory
}