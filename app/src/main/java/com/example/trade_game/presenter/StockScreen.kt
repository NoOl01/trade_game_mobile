package com.example.trade_game.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.domain.view.MarketViewModel

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
                .fillMaxSize()
        ){
            Text(
                text = "$assetId"
            )
        }
    }
}