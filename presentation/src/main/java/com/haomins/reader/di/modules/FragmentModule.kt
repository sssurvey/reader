package com.haomins.reader.di.modules

import com.haomins.reader.view.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeSourceTitleListFragment(): SourceTitleListFragment

    @ContributesAndroidInjector
    abstract fun contributeArticleListFragment(): ArticleListFragment

    @ContributesAndroidInjector
    abstract fun contributeArticleDetailFragment(): ArticleDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment
}