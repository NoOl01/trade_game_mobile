package com.example.trade_game.presenter

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.R
import com.example.trade_game.common.Montserrat
import com.example.trade_game.common.NoScrollWebView
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.BASE_URL
import com.example.trade_game.domain.view.MarketViewModel

@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
@Composable
fun StockScreen(
    assetId: Int,
    assetName: String,
    navController: NavController,
    isGestureNavigation: Boolean,
    viewModel: MarketViewModel = viewModel()
) {
    var amount by remember { mutableStateOf("1.00") }
    var error by remember { mutableStateOf("") }

    val ratingPadding = if (isGestureNavigation) 0.dp else 30.dp
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    val marketBuyData by viewModel.marketBuyData.collectAsState()
    val marketSellData by viewModel.marketSellData.collectAsState()

    LaunchedEffect(marketBuyData?.error) {
        if (marketBuyData?.error == "You don't have enough money") {
            error = "У вас недостаточно средств"
        }
    }

    LaunchedEffect(marketSellData?.error) {
        if (marketSellData?.error == "You don't have enough asset") {
            error = "У вас недостаточно акций"
        }
    }

    val mUrl = "https://$BASE_URL/?asset_id=$assetId"
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = ratingPadding)
            .padding(top = 34.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = assetName,
                    fontSize = 30.sp,
                    fontFamily = Montserrat,
                    color = Color(0xFF1641B7),
                    modifier = Modifier.align(Alignment.Center)
                )

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cross),
                        contentDescription = "Закрыть",
                        tint = Color(0xFF1641B7)
                    )
                }
            }
            Spacer(Modifier.height(30.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(15.dp))
            ) {
                AndroidView(
                    factory = {
                        NoScrollWebView(it).apply {
                            settings.javaScriptEnabled = true
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            settings.setSupportZoom(false)
                            settings.builtInZoomControls = false
                            settings.displayZoomControls = false
                            isVerticalScrollBarEnabled = false
                            isHorizontalScrollBarEnabled = false
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            webViewClient = WebViewClient()
                            loadUrl(mUrl)
                        }
                    }, update = {
                        it.loadUrl(mUrl)
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                )
            }
            Spacer(Modifier.height(10.dp))
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = error,
                    fontFamily = Montserrat,
                    fontSize = 18.sp,
                    color = Color(0xFFFF0000)
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() || it == '.' }

                        val dotCount = filtered.count { it == '.' }
                        if (dotCount <= 1) {
                            amount = filtered
                        }
                    },
                    textStyle = TextStyle(fontFamily = Montserrat),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = { Text("Количество акций") },
                    shape = RoundedCornerShape(15.dp),
                    colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1641B7), unfocusedBorderColor = Color(0xFF1641B7))
                )
                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OutlinedButton(
                        colors = ButtonColors(
                            containerColor = Color(0xFF1641B7),
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFF1641B7),
                            disabledContentColor = Color.White
                        ),
                        onClick = {
                            viewModel.marketBuy(amount.toFloat(), assetId, preferencesManager)
                        }
                    ) {
                        Text(
                            text = "Купить",
                            fontFamily = Montserrat,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(Modifier.width(20.dp))
                    OutlinedButton(
                        colors = ButtonColors(
                            containerColor = Color(0xFF1641B7),
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFF1641B7),
                            disabledContentColor = Color.White
                        ),
                        onClick = {
                            viewModel.marketSell(amount.toFloat(), assetId, preferencesManager)
                        }
                    ) {
                        Text(
                            text = "Продать",
                            fontFamily = Montserrat,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}