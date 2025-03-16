package com.example.trade_game.data.web_sockets

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketClient () {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect(url: String, listener: WebSocketListener, token: String){
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $token")
            .build()
        webSocket = client.newWebSocket(request, listener)

        Log.d("token", "Bearer $token")
    }

    fun sendMessage(message: String){
        webSocket?.send(message)
    }

    fun close(){
        webSocket?.close(1000, "Closed by user")
        webSocket = null
    }
}