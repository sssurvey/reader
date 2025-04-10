package com.haomins.wiring

import com.haomins.data.util.DataStorePrefUtilsImpl
import com.haomins.data.util.DateUtilsImpl
import com.haomins.domain.common.DateUtils
import com.haomins.domain.common.PrefUtils
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
    fun provideDefaultPrefDataStore(dataStorePrefUtilsImpl: DataStorePrefUtilsImpl)
            : PrefUtils


    @Singleton
    @Binds
    fun provideDateUtils(dateUtilsImpl: DateUtilsImpl): DateUtils
}