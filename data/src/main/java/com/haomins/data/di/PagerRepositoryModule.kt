package com.haomins.data.di

import com.haomins.data.datastore.paging.ArticleListPagerDataStore
import com.haomins.domain.repositories.ArticleListPagerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface PagerRepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindArticleListPagerRepository(articleListPagerDataStore: ArticleListPagerDataStore):
            ArticleListPagerRepository

}