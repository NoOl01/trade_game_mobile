package com.example.trade_game.domain.models

data class MarketTransactionResponse(
    val status: String,
    val data: TransactionResult?,
    val error: String?
)

data class TransactionResult(
    val amount: String,
    val commission: String,
    val balance: String
)