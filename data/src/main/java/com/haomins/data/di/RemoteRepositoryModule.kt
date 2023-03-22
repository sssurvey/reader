package com.haomins.data.di

import com.haomins.data.repositories.*
import com.haomins.data.repositories.remote.SubscriptionRemoteDataStore
import com.haomins.domain.repositories.*
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
    fun bindAddSourceRepository(addSourceRepository: AddSourceRepository):
            AddSourceRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindLoginRepository(loginRepository: LoginRepository):
            LoginRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindArticleDetailRepository(articleDetailRepository: ArticleDetailRepository):
            ArticleDetailRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindSubscriptionRemoteRepository(subscriptionRemoteDataStore: SubscriptionRemoteDataStore):
            SubscriptionRemoteRepository

    @Binds
    @ViewModelScoped
    fun bindArticleListRepository(articleListRepository: ArticleListRepository):
            ArticleListRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindLoggingRepository(loggingRepository: LoggingRepository):
            LoggingRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindContactInfoRepository(contactInfoRepository: ContactInfoRepository):
            ContactInfoRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindDisclosureRepository(disclosureRepository: DisclosureRepository):
            DisclosureRepositoryContract
}