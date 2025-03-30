package com.example.trade_game.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.domain.view.MarketViewModel
import com.jaikeerthick.composable_graphs.composables.line.LineGraph
import com.jaikeerthick.composable_graphs.composables.line.model.LineData

@Composable
fun StockScreen(assetId: Int, navController: NavController, isGestureNavigation: Boolean, viewModel: MarketViewModel = viewModel()){

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
            val data = listOf(LineData(x = "Sun", y = 200), LineData(x = "Mon", y = 40))
            LineGraph(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                data = data,
            )
        }
    }
}