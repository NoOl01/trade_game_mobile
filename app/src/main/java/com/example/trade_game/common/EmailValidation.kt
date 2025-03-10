package com.example.trade_game.common

import android.util.Patterns

fun isValidEmail(email: String): Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}