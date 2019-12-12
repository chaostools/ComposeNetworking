package com.example.composenetworking.network

import com.example.composenetworking.data.PaperNew
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface APIService {
    @GET("everything")
    fun getNewPaperAsync(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("q") query: String
    ): Deferred<PaperNew>


    companion object {
        fun create(): APIService {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging) // same for .addInterceptor(...)
                .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()


            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://newsapi.org/v2/")
                .addCallAdapterFactory(
                    CoroutineCallAdapterFactory()
                )
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .build()

            return retrofit.create(APIService::class.java)
        }
    }


}