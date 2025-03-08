package com.example.trade_game.domain.models

data class RegisterResponse(
    val status: String,
    val data: List<RegisterData>,
    val error: String?
)

data class RegisterData(
    val access_token: String,
    val refresh_token: String,
    val email: String,
    val username: String
)