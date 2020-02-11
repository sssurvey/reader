package com.haomins.reader

import android.app.Application
import com.haomins.reader.networks.Url
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReaderApplication : Application() {

    private lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()
        init()
    }

    fun getRetrofitClient(): Retrofit {
        return retrofit
    }

    private fun init() {
        buildRetrofitClient(getLoggedHttpClient())
    }

    private fun getLoggedHttpClient(): OkHttpClient {
        val httpLogInterceptor = HttpLoggingInterceptor()
        val httpClientBuilder = OkHttpClient.Builder()
        httpLogInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        httpClientBuilder.addInterceptor(httpLogInterceptor)
        return httpClientBuilder.build()
    }

    private fun buildRetrofitClient(httpClientBuilder: OkHttpClient) {
        retrofit = Retrofit.Builder().apply {
            baseUrl(Url.BASE_URL.string)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            client(httpClientBuilder)
        }.build()
    }

}