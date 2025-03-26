package com.example.trade_game.domain.models

data class UserInfoResponse(
    val status: String,
    val data: UserInfoResponseData?,
    val error: String?
)

data class UserInfoResponseData(
    val id: Int,
    val username: String,
    val email: String,
    val balance: String,
    val created_at: String
)