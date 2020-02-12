package com.haomins.reader.di.modules

import com.haomins.reader.fragments.list.SourceTitleListFragment
import com.haomins.reader.fragments.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeSourceTitleListFragment(): SourceTitleListFragment

}