package com.example.trade_game.domain.models

data class AuthResponse(
    val status: String,
    val data: AuthData?,
    val error: String?
)

data class AuthData(
    val access_token: String,
    val refresh_token: String,
    val email: String,
    val username: String
)