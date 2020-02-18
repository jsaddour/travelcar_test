package com.example.travelcartest.network

import com.grapesnberries.curllogger.CurlLoggerInterceptor

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceGeneratorAPI {

    fun <S> createService(url: String, serviceClass: Class<S>): S {
        val service = Retrofit.Builder()
            .baseUrl(url)
            .client(createHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        return service.create(serviceClass)
    }

    private fun createHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY // Log Headers + Body
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(CurlLoggerInterceptor()) // API 16

        return httpClient.build()
    }
}