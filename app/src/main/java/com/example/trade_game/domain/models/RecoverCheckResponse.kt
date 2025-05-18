package com.example.trade_game.domain.models

data class RecoverCheckResponse (
    val status: String,
    val data: RecoverData?,
    val error: String?
)

data class RecoverData (
    val access_token: String
)