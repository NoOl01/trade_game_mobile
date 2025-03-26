package com.example.trade_game.domain.models

data class ChatsResponse (
    val status: String,
    val data: List<ChatData>,
    val error: String?
)

data class ChatData(
    val id: Int,
    val username: String
)