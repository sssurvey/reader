package com.haomins.data.di.module

import com.haomins.data.strategies.DefaultSchedulingStrategy
import com.haomins.data.strategies.RxSchedulingStrategy
import dagger.Binds
import dagger.Module

@Module
interface RxModule {

    @Binds
    fun provideRxSchedulingPolicy(defaultSchedulingPolicy: DefaultSchedulingStrategy): RxSchedulingStrategy

}