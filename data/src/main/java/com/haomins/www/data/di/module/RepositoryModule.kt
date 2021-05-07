package com.haomins.www.data.di.module

import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.haomins.domain.repositories.ArticleDetailRepositoryContract
import com.haomins.domain.repositories.LoginRepositoryContract
import com.haomins.www.data.repositories.AddSourceRepository
import com.haomins.www.data.repositories.ArticleDetailRepository
import com.haomins.www.data.repositories.LoginRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindsAddSourceRepository(addSourceRepository: AddSourceRepository): AddSourceRepositoryContract

    @Binds
    fun bindsLoginRepository(loginRepository: LoginRepository): LoginRepositoryContract

    @Binds
    fun bindsArticleDetailRepository(articleDetailRepository: ArticleDetailRepository): ArticleDetailRepositoryContract

}