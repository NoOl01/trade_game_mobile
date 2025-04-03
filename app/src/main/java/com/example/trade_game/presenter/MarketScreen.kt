package com.example.trade_game.presenter

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trade_game.domain.BASE_URL
import com.example.trade_game.domain.models.WebSocketMarketResponse
import com.example.trade_game.domain.web_sockets.WebSocketManager
import com.example.trade_game.presenter.components.StockCard
import org.json.JSONObject

@Composable
fun MarketScreen(navController: NavController, isGestureNavigation: Boolean) {
    val ratingPadding = if (isGestureNavigation) 0.dp else 30.dp

    val webSocketManager = remember { WebSocketManager("wss://${BASE_URL}/api/v1/market/data") }
    var stocks by remember { mutableStateOf<List<WebSocketMarketResponse>>(emptyList()) }

    var error by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        webSocketManager.connect { jsonMessage ->
            try {
                val jsonObject = JSONObject(jsonMessage)

                val stockList = mutableListOf<WebSocketMarketResponse>()

                for (key in jsonObject.keys()) {
                    val stockJson = jsonObject.getJSONObject(key)
                    val stock = WebSocketMarketResponse(
                        id = key.toInt(),
                        symbol = stockJson.getString("symbol"),
                        name = stockJson.getString("name"),
                        price = stockJson.getString("price"),
                        trend = stockJson.getString("trend"),
                        changePercent = stockJson.getString("change_percent")
                    )
                    stockList.add(stock)
                }

                stocks = stockList.sortedBy { it.id }
                loading = false
                Log.d("WebSocket", "Parsed stocks: $stocks")
            } catch (e: Exception) {
                error = true
                Log.e("WebSocket", "Error parsing JSON", e)
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(top = ratingPadding)
            .fillMaxSize()
    ) {
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xFF1641B7)
                )
            }

        } else {
            LazyColumn {
                items(stocks) { stock ->
                    StockCard(stock, navController)
                }
                item {
                    Spacer(Modifier.height(60.dp + ratingPadding))
                }
            }
        }
    }

}