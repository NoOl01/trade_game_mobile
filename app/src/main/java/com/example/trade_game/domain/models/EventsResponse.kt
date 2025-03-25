package com.example.trade_game.domain.models

data class EventsResponse(
    val status: String,
    val data: List<Event>,
    val error: String?
)

data class Event (
    val id: Int,
    val title: String,
    val description: String,
    val created_at: String
)
