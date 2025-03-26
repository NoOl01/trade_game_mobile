package com.example.trade_game.domain.models

data class UsersTopResponse(
    val status: String,
    val data: List<TopUser>,
    val error: String?
)

data class TopUser (
    val id: Int,
    val username: String,
    val total_balance: String,
)
