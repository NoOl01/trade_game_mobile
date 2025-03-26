package com.example.trade_game.domain.models

data class UserPlaceResponse(
    val status: String,
    val data: PlaceData?,
    val error: String?
)

data class PlaceData (
    val place: Int,
    val total_balance: String
)