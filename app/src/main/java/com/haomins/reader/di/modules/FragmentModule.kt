package com.haomins.reader.di.modules

import com.haomins.reader.view.fragments.ArticleDetailFragment
import com.haomins.reader.view.fragments.ArticleListFragment
import com.haomins.reader.view.fragments.LoginFragment
import com.haomins.reader.view.fragments.SourceTitleListFragment
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
}