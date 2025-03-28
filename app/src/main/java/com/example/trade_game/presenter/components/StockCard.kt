package com.example.trade_game.presenter.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trade_game.R
import com.example.trade_game.common.Montserrat
import com.example.trade_game.domain.models.WebSocketMarketResponse

@Composable
fun StockCard(stock: WebSocketMarketResponse, navController: NavController) {
    val interactionSource = remember { MutableInteractionSource() }
    val trendIcon = when (stock.trend) {
        "up" -> R.drawable.change_up
        "down" -> R.drawable.change_down
        else -> R.drawable.change_up
    }
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .height(60.dp)
            .clickable (
                interactionSource = interactionSource,
                indication = null
            ){
                navController.navigate("StockScreen")
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1641B7)),
            contentAlignment = Alignment.CenterStart
        ) {
            Row (
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                        .background(Color.White),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(6.dp))
                    Row(
                        modifier = Modifier
                            .padding(2.dp)
                            .width(70.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color(0xFF1641B7)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stock.symbol,
                            textAlign = TextAlign.Center,
                            fontFamily = Montserrat,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        text = stock.name,
                        color = Color.Black,
                        fontFamily = Montserrat
                    )
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1641B7)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$${stock.price}",
                            fontSize = 20.sp,
                            modifier = Modifier.height(20.dp),
                            fontFamily = Montserrat,
                            color = Color.White
                        )
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = trendIcon),
                                contentDescription = "trend"
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text = "${stock.changePercent}%",
                                fontSize = 14.sp,
                                fontFamily = Montserrat,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
    Spacer(Modifier.height(20.dp))
}