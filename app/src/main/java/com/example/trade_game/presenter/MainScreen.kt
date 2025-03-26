package com.example.trade_game.presenter

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.R
import com.example.trade_game.common.Montserrat
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.data.web_sockets.WebSocketManager
import com.example.trade_game.domain.BASE_URL
import com.example.trade_game.domain.models.WebSocketMarketResponse
import com.example.trade_game.domain.view.MainViewModel
import com.example.trade_game.presenter.components.StockCard
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun MainView(navController: NavController, viewModel: MainViewModel = viewModel()) {
    var stocks by remember { mutableStateOf<List<WebSocketMarketResponse>>(emptyList()) }
    var search by remember { mutableStateOf("") }

    var error by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val scope = rememberCoroutineScope()
    val userInfo by viewModel.userInfo.collectAsState()

    val webSocketManager = remember { WebSocketManager("wss://${BASE_URL}/api/v1/market/data") }

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getUserInfo(preferencesManager)
        }
        webSocketManager.connect { jsonMessage ->
            try {
                val jsonObject = JSONObject(jsonMessage)

                val stockList = mutableListOf<WebSocketMarketResponse>()

                for (key in jsonObject.keys()) {
                    val stockJson = jsonObject.getJSONObject(key)
                    val stock = WebSocketMarketResponse(
                        id = key.toInt(),
                        symbol = stockJson.getString("symbol"),
                        price = stockJson.getString("price"),
                        trend = stockJson.getString("trend"),
                        changePercent = stockJson.getString("change_percent")
                    )
                    stockList.add(stock)
                }

                stocks = stockList.sortedBy { it.id }

                Log.d("WebSocket", "Parsed stocks: $stocks")
            } catch (e: Exception) {
                error = true
                Log.e("WebSocket", "Error parsing JSON", e)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(top = 34.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)
            ) {
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .border(1.dp, Color(0xFF2A41DA), RoundedCornerShape(40.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск",
                        tint = Color(0xFF06080F)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (search.isEmpty()) {
                            Text(
                                text = "поиск...",
                                color = Color(0xB0364CDF),
                                fontSize = 16.sp,
                                fontFamily = Montserrat
                            )
                        }
                        BasicTextField(
                            value = search,
                            onValueChange = { search = it },
                            textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF2A41DA)),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "UserIcon"
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = userInfo?.data?.username ?: "Загрузка...",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(horizontal = 24.dp)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(25.dp)
                        .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                        .background(Color(0xFF89A9FF))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(topEnd = 15.dp))
                            .background(Color(0xFF1641B7))

                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF89A9FF))
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp,
                                topEnd = 20.dp
                            )
                        )
                        .background(Color(0xFF1641B7)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ваш баланс",
                            fontFamily = Montserrat,
                            color = Color.White
                        )
                        Text(
                            text = "$${userInfo?.data?.balance ?: "0"}",
                            fontFamily = Montserrat,
                            fontSize = 30.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            LazyColumn {
                items(stocks) { stock ->
                    StockCard(stock)
                }
            }
        }
        if (error) {

            Popup(
                alignment = Alignment.Center
            ) {
                Text("ОШИБКА", color = Color.White)
            }
        }
        if (loading) {
            Popup(
                alignment = Alignment.Center,
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xFF040331)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.White
                    )
                }
            }
        }
    }
}