package com.haomins.data.di

import com.haomins.data.repositories.*
import com.haomins.domain.repositories.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindsAddSourceRepository(addSourceRepository: AddSourceRepository):
            AddSourceRepositoryContract

    @Binds
    fun bindsLoginRepository(loginRepository: LoginRepository):
            LoginRepositoryContract

    @Binds
    fun bindsArticleDetailRepository(articleDetailRepository: ArticleDetailRepository):
            ArticleDetailRepositoryContract

    @Binds
    fun bindsSourceSubscriptionListRepository(sourceSubscriptionListRepository: SourceSubscriptionListRepository):
            SourceSubscriptionListRepositoryContract

    @Binds
    fun bindsArticleListRepository(articleListRepository: ArticleListRepository):
            ArticleListRepositoryContract

    @Binds
    fun bindsLoggingRepository(loggingRepository: LoggingRepository):
            LoggingRepositoryContract

    @Binds
    fun bindsContactInfoRepository(contactInfoRepository: ContactInfoRepository):
            ContactInfoRepositoryContract

    @Binds
    fun bindsDisclosureRepository(disclosureRepository: DisclosureRepository):
            DisclosureRepositoryContract
}