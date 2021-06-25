package com.haomins.data.di.module

import com.haomins.data.repositories.*
import com.haomins.domain.repositories.*
import dagger.Binds
import dagger.Module

@Module
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
}