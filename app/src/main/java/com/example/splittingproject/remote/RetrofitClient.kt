package com.example.splittingproject.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created By Huzayfa elhussein on 11/09/2021
 */

object RetrofitClient {

    private var retrofit: Retrofit? = null


    fun getClient(baseUrl: String): Retrofit? {

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
        if (retrofit == null) {
            val httpClient: OkHttpClient = client.build()
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient)
                .build()
        }
        return retrofit
    }


}