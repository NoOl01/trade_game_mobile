package com.example.trade_game.domain.models

data class CreateOrderResponse(
    val status: String,
    val data: OrderId?,
    val error: String?
)

data class OrderId (
    val order_id: Int
)