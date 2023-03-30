package com.haomins.data.di

import com.haomins.data.datastore.*
import com.haomins.data.datastore.remote.AddSourceRemoteDataStore
import com.haomins.data.datastore.remote.ArticleListRemoteDataStore
import com.haomins.data.datastore.remote.LoginRemoteDataStore
import com.haomins.data.datastore.remote.SubscriptionRemoteDataStore
import com.haomins.domain.repositories.*
import com.haomins.domain.repositories.remote.AddSourceRemoteRepository
import com.haomins.domain.repositories.remote.ArticleListRemoteRepository
import com.haomins.domain.repositories.remote.LoginRemoteRepository
import com.haomins.domain.repositories.remote.SubscriptionRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RemoteRepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindAddSourceRemoteRepository(addSourceRemoteDataStore: AddSourceRemoteDataStore):
            AddSourceRemoteRepository

    @Binds
    @ViewModelScoped
    fun bindLoginRemoteRepository(loginRemoteDataStore: LoginRemoteDataStore):
            LoginRemoteRepository

    @Binds
    @ViewModelScoped
    fun bindSubscriptionRemoteRepository(subscriptionRemoteDataStore: SubscriptionRemoteDataStore):
            SubscriptionRemoteRepository

    @Binds
    @ViewModelScoped
    fun bindArticleListRemoteRepository(articleListRemoteDataStore: ArticleListRemoteDataStore):
            ArticleListRemoteRepository

}