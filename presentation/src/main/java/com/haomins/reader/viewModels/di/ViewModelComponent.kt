package com.haomins.reader.viewModels.di

import com.haomins.reader.view.activities.MainActivity
import com.haomins.reader.view.fragments.*
import com.haomins.reader.viewModels.di.modules.ViewModelModule
import dagger.Subcomponent

@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelComponent {

    // fragments
    fun inject(addSourceFragment: AddSourceFragment)

    fun inject(articleDetailFragment: ArticleDetailFragment)

    fun inject(articleListFragment: ArticleListFragment)

    fun inject(loginFragment: LoginFragment)

    fun inject(settingsFragment: SettingsFragment)

    fun inject(sourceTitleListFragment: SourceTitleListFragment)

    // activities
    fun inject(mainActivity: MainActivity)

    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelComponent
    }

}