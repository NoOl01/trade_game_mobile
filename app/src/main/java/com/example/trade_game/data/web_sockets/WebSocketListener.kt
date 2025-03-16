package com.example.trade_game.data.web_sockets

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class WebSocketListenerClient : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d("websocket", "onOpen: $response")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.d("websocket", "onMessage: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.d("websocket", "onMessage (bytes): $bytes")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        webSocket.close(1000, null)
        Log.d("websocket", "onClosing: $code, reason: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, reason: Response?){
        Log.d("websocket", "WebSocket Failure: ${t.message}")
    }
}