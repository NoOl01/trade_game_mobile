package com.example.trade_game.domain.models

data class CancelOrderResponse(
    val status: String,
    val data: String?,
    val error: String?
)