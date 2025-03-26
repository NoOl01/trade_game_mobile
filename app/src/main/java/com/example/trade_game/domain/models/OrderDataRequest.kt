package com.example.trade_game.domain.models

data class OrderDataRequest(
    val amount: Float,
    val asset_id: Int,
    val order_type: String,
    val price: Float
)
