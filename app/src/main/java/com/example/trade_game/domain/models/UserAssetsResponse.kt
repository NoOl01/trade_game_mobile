package com.example.trade_game.domain.models

data class UserAssetsResponse(
    val status: String,
    val data: List<UserAsset>,
    val error: String?
)

data class UserAsset (
    val id: Int,
    val symbol: String,
    val name: String,
    val amount: String,
)