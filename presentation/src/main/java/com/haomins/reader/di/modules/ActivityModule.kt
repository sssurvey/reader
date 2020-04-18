package com.haomins.reader.di.modules

import com.haomins.reader.view.activities.ArticleDetailActivity
import com.haomins.reader.view.activities.ArticleListActivity
import com.haomins.reader.view.activities.MainActivity
import com.haomins.reader.view.activities.SettingsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeArticleListActivity(): ArticleListActivity

    @ContributesAndroidInjector
    abstract fun contributeArticleDetailActivity(): ArticleDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeSettingsActivity(): SettingsActivity

}