package com.example.trade_game.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://i2zngctv95jp.share.zrok.io"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: RetrofitApi by lazy {
        retrofit.create(RetrofitApi::class.java)
    }
}