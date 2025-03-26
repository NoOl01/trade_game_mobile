package com.example.trade_game.domain.models

data class ExecuteOrderResponse(
    val status: String,
    val data: BalanceData?,
    val error: String?
)

data class BalanceData (
    val balance: String
)