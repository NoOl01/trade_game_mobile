package com.example.trade_game.domain

import com.example.trade_game.domain.models.AuthResponse
import com.example.trade_game.domain.models.LoginRequest
import com.example.trade_game.domain.models.RefreshRequest
import com.example.trade_game.domain.models.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitApi{
    @POST("api/v1/auth/register")
    suspend fun register(@Body registerReq: RegisterRequest): AuthResponse

    @POST("api/v1/auth/login")
    suspend fun login(@Body loginReq: LoginRequest): AuthResponse

    @POST("/api/v1/auth/refresh")
    suspend fun refresh(@Body refreshReq: RefreshRequest): AuthResponse
}