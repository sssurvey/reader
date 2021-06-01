package com.haomins.data.di.module

import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.haomins.domain.repositories.ArticleDetailRepositoryContract
import com.haomins.domain.repositories.LoginRepositoryContract
import com.haomins.domain.repositories.SourceSubscriptionListRepositoryContract
import com.haomins.data.repositories.AddSourceRepository
import com.haomins.data.repositories.ArticleDetailRepository
import com.haomins.data.repositories.LoginRepository
import com.haomins.data.repositories.SourceSubscriptionListRepository
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

}