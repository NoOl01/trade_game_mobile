package com.example.trade_game.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatDateTime(dateTimeString: String): String {
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)
    return dateTime.format(outputFormatter)
}

fun formatTime(dateTimeString: String): String {
    val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val dateTime = LocalDateTime.parse(dateTimeString, inputFormatter)
    return dateTime.format(outputFormatter)
}