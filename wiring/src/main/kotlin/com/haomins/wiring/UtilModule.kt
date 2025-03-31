package com.haomins.wiring

import com.haomins.data.util.DateUtilsImpl
import com.haomins.data.util.SharedPrefUtilsImpl
import com.haomins.domain.common.DateUtils
import com.haomins.domain.common.SharedPrefUtils
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UtilModule {

    @Singleton
    @Binds
    fun provideSharedPrefUtils(sharedPrefUtilsImpl: SharedPrefUtilsImpl)
            : SharedPrefUtils


    @Singleton
    @Binds
    fun provideDateUtils(dateUtilsImpl: DateUtilsImpl): DateUtils
}