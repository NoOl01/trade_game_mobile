package com.example.trade_game.domain.models

data class RegisterRequest(
    val email: String,
    val username: String,
    val password: String
)