package com.haomins.www.data.di.module

import com.haomins.www.data.service.TheOldReaderService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object ConnectionModule {

    @Reusable
    @JvmStatic
    @Provides
    fun provideTheOldReaderService(okHttpClient: OkHttpClient): TheOldReaderService {
        return Retrofit.Builder().apply {
            baseUrl(TheOldReaderService.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            client(okHttpClient)
        }.build().create(TheOldReaderService::class.java)
    }

    @Reusable
    @JvmStatic
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val httpLogInterceptor = HttpLoggingInterceptor()
        val httpClientBuilder = OkHttpClient.Builder()
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(httpLogInterceptor)
        return httpClientBuilder.build()
    }
}