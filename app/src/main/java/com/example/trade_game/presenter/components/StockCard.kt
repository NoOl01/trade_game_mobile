package com.example.trade_game.presenter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.trade_game.domain.models.WebSocketMarketResponse

@Composable
fun StockCard(stock: WebSocketMarketResponse){
    Spacer(Modifier.height(10.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFA2A2A2)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row (
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Text(
                text = stock.symbol,
                modifier = Modifier
                    .background(Color(0xFF1641B7))
                    .padding(2.dp)
                    .width(40.dp)
                    .clip(RoundedCornerShape(10.dp)),
                textAlign = TextAlign.Center
            )
        }
    }
}