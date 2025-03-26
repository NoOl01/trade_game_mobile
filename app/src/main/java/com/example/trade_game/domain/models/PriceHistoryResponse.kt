package com.example.trade_game.domain.models

data class PriceHistoryResponse(
    val status: String,
    val data: List<PriceData>,
    val error: String?
)

data class PriceData(
    val price: String,
    val timestamp: String
)