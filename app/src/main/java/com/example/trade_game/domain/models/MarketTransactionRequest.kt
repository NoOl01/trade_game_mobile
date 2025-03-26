package com.example.trade_game.domain.models

data class MarketTransactionRequest(
    val amount: Float,
    val asset_id: Int
)