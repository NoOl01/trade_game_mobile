package com.example.trade_game.domain.web_sockets

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class WebSocketManager(private val url: String) {
    private val client = OkHttpClient.Builder()
        .pingInterval(10, TimeUnit.SECONDS)
        .build()
    private var webSocket: WebSocket? = null

    fun connect(accessToken: String?, onMessageReceived: (String) -> Unit){
        val request = Request.Builder().url(url)
        if (accessToken != null) {
            request.addHeader("Authorization", "Bearer $accessToken")
        }
        val buildRequest = request.build()
        webSocket = client.newWebSocket(buildRequest, object : WebSocketListener(){

            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessageReceived(text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                val text = bytes.utf8()
                onMessageReceived(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {}

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
            }
        })
    }

    fun sendMessage(recipientId: Int, message: String): Boolean{
        val messageToSend = JSONObject().apply {
            put("recipient_id", recipientId)
            put("text", message)
        }.toString()
        return webSocket!!.send(messageToSend)
    }

    fun disconnect(){
        webSocket?.close(1000, "Отключение")
        client.dispatcher.executorService.shutdown()
    }
}