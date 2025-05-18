package com.example.trade_game.domain.models

data class RecoverCheckRequest(
    val email: String,
    val code: Int
)