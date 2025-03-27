package com.example.trade_game.domain.models

data class WebSocketMarketResponse(
    val id: Int,
    val symbol: String,
    val name: String,
    val price: String,
    val trend: String,
    val changePercent: String
)
