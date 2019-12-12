package com.example.composenetworking.network

import com.example.composenetworking.data.PaperNew
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("top-headlines")
    fun getNewPaper(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Deferred<PaperNew>


    companion object {
        fun create(): APIService {

            val retrofit = Retrofit.Builder()
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