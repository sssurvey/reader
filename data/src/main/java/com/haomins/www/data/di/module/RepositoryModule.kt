package com.haomins.www.data.di.module

import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.haomins.www.data.repositories.AddSourceRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindsAddSourceRepository(addSourceRepository: AddSourceRepository): AddSourceRepositoryContract

}