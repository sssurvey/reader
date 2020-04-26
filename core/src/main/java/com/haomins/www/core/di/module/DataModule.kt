package com.haomins.www.core.di.module

import com.haomins.www.core.service.TheOldReaderService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object DataModule {

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