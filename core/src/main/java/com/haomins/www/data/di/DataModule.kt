package com.haomins.www.data.di

import android.app.Application
import androidx.room.Room
import com.haomins.www.data.service.TheOldReaderService
import com.haomins.www.data.db.AppDatabase
import com.haomins.www.data.db.RoomService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideTheOldReaderService(): TheOldReaderService {

        fun getLoggedHttpClient(): OkHttpClient {
            val httpLogInterceptor = HttpLoggingInterceptor()
            val httpClientBuilder = OkHttpClient.Builder()
            httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(httpLogInterceptor)
            return httpClientBuilder.build()
        }

        return Retrofit.Builder().apply {
            baseUrl(TheOldReaderService.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            client(getLoggedHttpClient())
        }.build().create(TheOldReaderService::class.java)
    }
}