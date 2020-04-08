package com.haomins.reader.di.modules

import android.app.Application
import androidx.room.Room
import com.haomins.reader.TheOldReaderService
import com.haomins.www.data.db.AppDatabase
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
class AppModule {

    @Singleton
    @Provides
    fun provideTheOldReaderService(): TheOldReaderService {

        fun getLoggedHttpClient(): OkHttpClient {
            val httpLogInterceptor = HttpLoggingInterceptor()
            val httpClientBuilder = OkHttpClient.Builder()
            httpLogInterceptor.level = HttpLoggingInterceptor.Level.BASIC
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

    @Singleton
    @Provides
    fun provideRoom(application: Application): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .build()
    }
}