package com.vitali.coboxtestapp

import me.toptas.rssconverter.RssConverterFactory
import me.toptas.rssconverter.RssFeed
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

interface AppService {
    @GET
    fun getRss(@Url url: String): Call<RssFeed>

    companion object {
        fun create():AppService
        {

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .addInterceptor(LogJsonInterceptor())
                .build()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(RssConverterFactory.create())
                .baseUrl("https://github.com")
                .build()

            return retrofit.create(AppService::class.java)
        }
    }
}