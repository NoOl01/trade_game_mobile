package com.example.trade_game.domain

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "3ekc5ameiwzf.share.zrok.io"

object RetrofitInstance {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://$BASE_URL")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: RetrofitApi by lazy {
        retrofit.create(RetrofitApi::class.java)
    }
}