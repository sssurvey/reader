package com.haomins.www.core.di.module

import com.haomins.www.core.policy.RxSchedulingPolicy
import com.haomins.www.core.policy.DefaultSchedulingPolicy
import dagger.Binds
import dagger.Module

@Module
interface RxModule {

    @Binds
    fun provideRxSchedulingPolicy(defaultSchedulingPolicy: DefaultSchedulingPolicy): RxSchedulingPolicy

}