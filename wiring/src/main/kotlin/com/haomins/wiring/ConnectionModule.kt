package com.haomins.wiring

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.scheduler.ExecutionScheduler
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConnectionModule {

    @Singleton
    @Provides
    fun provideTheOldReaderService(
        okHttpClient: OkHttpClient,
        executionScheduler: ExecutionScheduler,
    ): TheOldReaderService {
        return Retrofit.Builder().apply {
            baseUrl(TheOldReaderService.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(
                RxJava2CallAdapterFactory
                    .createWithScheduler(executionScheduler.scheduler)
            )
            client(okHttpClient)
        }.build().create(TheOldReaderService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLogInterceptor = HttpLoggingInterceptor()
        val httpClientBuilder = OkHttpClient.Builder()
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        httpClientBuilder.addInterceptor(httpLogInterceptor)
        return httpClientBuilder.build()
    }
}