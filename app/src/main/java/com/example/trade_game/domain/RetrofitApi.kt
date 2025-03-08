package com.example.trade_game.domain

import com.example.trade_game.domain.models.LoginRequest
import com.example.trade_game.domain.models.RegisterRequest
import com.example.trade_game.domain.models.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitApi{
    @POST("api/v1/auth/register")
    suspend fun register(@Body registerReq: RegisterRequest): RegisterResponse

    @POST("api/v1/auth/login")
    suspend fun login(@Body loginReq: LoginRequest): RegisterResponse
}