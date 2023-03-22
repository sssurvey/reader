package com.haomins.data.di

import com.haomins.data.repositories.local.*
import com.haomins.domain.repositories.local.ArticleDetailLocalRepository
import com.haomins.domain.repositories.local.ContactInfoLocalRepository
import com.haomins.domain.repositories.local.DisclosureLocalRepository
import com.haomins.domain.repositories.local.LoggingLocalRepository
import com.haomins.domain.repositories.local.SubscriptionLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface LocalRepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindSubscriptionLocalRepository(
        subscriptionLocalDataStore: SubscriptionLocalDataStore
    ): SubscriptionLocalRepository

    @Binds
    @ViewModelScoped
    fun bindArticleDetailLocalRepository(articleDetailLocalDataStore: ArticleDetailLocalDataStore):
            ArticleDetailLocalRepository

    @Binds
    @ViewModelScoped
    fun bindLoggingLocalRepository(loggingLocalDataStore: LoggingLocalDataStore):
            LoggingLocalRepository

    @Binds
    @ViewModelScoped
    fun bindContactInfoLocalRepository(contactInfoLocalDataStore: ContactInfoLocalDataStore):
            ContactInfoLocalRepository

    @Binds
    @ViewModelScoped
    fun bindDisclosureLocalRepository(disclosureLocalDataStore: DisclosureLocalDataStore):
            DisclosureLocalRepository

}