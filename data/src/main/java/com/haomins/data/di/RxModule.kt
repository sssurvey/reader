package com.haomins.data.di

import com.haomins.data.strategies.DefaultSchedulingStrategy
import com.haomins.data.strategies.RxSchedulingStrategy
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RxModule {

    @Binds
    @Singleton
    fun provideRxSchedulingPolicy(defaultSchedulingPolicy: DefaultSchedulingStrategy): RxSchedulingStrategy

}