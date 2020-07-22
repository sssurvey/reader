package com.haomins.www.core.di.module

import com.haomins.www.core.strategies.RxSchedulingStrategy
import com.haomins.www.core.strategies.DefaultSchedulingStrategy
import dagger.Binds
import dagger.Module

@Module
interface RxModule {

    @Binds
    fun provideRxSchedulingPolicy(defaultSchedulingPolicy: DefaultSchedulingStrategy): RxSchedulingStrategy

}