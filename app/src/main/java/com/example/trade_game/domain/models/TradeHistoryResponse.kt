package com.example.trade_game.domain.models

data class TradeHistoryResponse(
    val status: String,
    val data: List<TradeData>,
    val error: String?
)

data class TradeData(
    val id: Int,
    val trade_type: String,
    val name: String,
    val price: String,
    val amount: String,
    val created_at: String
)