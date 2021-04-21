package com.haomins.www.model.di.module

import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.haomins.www.model.repositories.AddSourceRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindsAddSourceRepository(addSourceRepository: AddSourceRepository): AddSourceRepositoryContract

}