package com.haomins.data.di

import com.haomins.data.repositories.local.SubscriptionLocalDataStore
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

}