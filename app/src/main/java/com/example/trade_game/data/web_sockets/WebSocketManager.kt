package com.example.trade_game.data.web_sockets

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketManager(private val url: String) {
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect(onMessageReceived: (String) -> Unit){
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener(){

            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessageReceived(text)
                Log.d("webSockets", "message: $text")
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                val text = bytes.utf8()
                onMessageReceived(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.d("webSockets", "${t.message}")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                Log.d("webSockets", "closed. Reason: $reason, Code: $code")
            }
        })
    }

    fun disconnect(){
        webSocket?.close(1000, "Отключение")
        client.dispatcher.executorService.shutdown()
    }
}