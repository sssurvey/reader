package com.haomins.data.di

import com.haomins.data.repositories.*
import com.haomins.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindsAddSourceRepository(addSourceRepository: AddSourceRepository):
            AddSourceRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindsLoginRepository(loginRepository: LoginRepository):
            LoginRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindsArticleDetailRepository(articleDetailRepository: ArticleDetailRepository):
            ArticleDetailRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindsSourcesRepository(sourcesRemoteDataStore: SourcesRemoteDataStore):
            SourcesRepository

    @Binds
    @ViewModelScoped
    fun bindsArticleListRepository(articleListRepository: ArticleListRepository):
            ArticleListRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindsLoggingRepository(loggingRepository: LoggingRepository):
            LoggingRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindsContactInfoRepository(contactInfoRepository: ContactInfoRepository):
            ContactInfoRepositoryContract

    @Binds
    @ViewModelScoped
    fun bindsDisclosureRepository(disclosureRepository: DisclosureRepository):
            DisclosureRepositoryContract
}