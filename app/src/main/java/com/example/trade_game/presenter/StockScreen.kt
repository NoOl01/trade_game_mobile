package com.example.trade_game.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.domain.view.MarketViewModel
import com.jaikeerthick.composable_graphs.composables.line.LineGraph
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun StockScreen(assetId: Int, navController: NavController, isGestureNavigation: Boolean, viewModel: MarketViewModel = viewModel()){
    val marketHistory by viewModel.marketHistory.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPriceHistory(assetId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Column (
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            marketHistory?.data?.let { history ->


                val chartData = history.map { priceData ->
                    val instant = Instant.ofEpochSecond(priceData.timestamp.toLong())
                    val localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime()
                    val formated = DateTimeFormatter.ofPattern("HH:mm:ss")

                    LineData(
                        x = localDateTime.format(formated),
                        y = priceData.price.toFloat()
                    )
                }

                LineGraph(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    data = chartData,
                )
            }
        }
    }
}