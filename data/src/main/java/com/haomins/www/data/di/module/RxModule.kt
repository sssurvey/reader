package com.haomins.www.data.di.module

import com.haomins.www.data.strategies.DefaultSchedulingStrategy
import com.haomins.www.data.strategies.RxSchedulingStrategy
import dagger.Binds
import dagger.Module

@Module
interface RxModule {

    @Binds
    fun provideRxSchedulingPolicy(defaultSchedulingPolicy: DefaultSchedulingStrategy): RxSchedulingStrategy

}