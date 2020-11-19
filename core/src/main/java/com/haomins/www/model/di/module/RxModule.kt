package com.haomins.www.model.di.module

import com.haomins.www.model.strategies.RxSchedulingStrategy
import com.haomins.www.model.strategies.DefaultSchedulingStrategy
import dagger.Binds
import dagger.Module

@Module
interface RxModule {

    @Binds
    fun provideRxSchedulingPolicy(defaultSchedulingPolicy: DefaultSchedulingStrategy): RxSchedulingStrategy

}