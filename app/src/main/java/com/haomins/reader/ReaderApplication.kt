package com.haomins.reader

import android.app.Application
import com.haomins.reader.networks.Url
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
        retrofit = Retrofit.Builder().apply {
            baseUrl(Url.BASE_URL.string)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

}