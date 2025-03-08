package com.example.trade_game.common

import android.util.Patterns

//val emailRegex = """^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$""".toRegex()

fun isValidEmail(email: String): Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}