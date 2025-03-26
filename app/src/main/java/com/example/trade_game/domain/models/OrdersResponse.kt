package com.example.trade_game.domain.models

data class OrdersResponse(
    val status: String,
    val data: List<OrderData>,
    val error: String?
)

data class OrderData (
    val id: Int,
    val user_id: Int,
    val username: String,
    val asset_id: Int,
    val order_type: String,
    val price: String,
    val amount: String,
    val status: String,
    val created_at: String,
    val updated_at: String,
)