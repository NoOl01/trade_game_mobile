package com.example.trade_game.domain.models

data class ChatHistoryResponse(
    val status: String,
    val data: List<ChatHistoryData?>,
    val error: String?
)

data class ChatHistoryData(
    val message_id: Int,
    val from_id: Int,
    val recipient_id: Int,
    val text: String,
    val created_at: String
)
