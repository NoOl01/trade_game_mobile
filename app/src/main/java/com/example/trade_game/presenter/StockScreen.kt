package com.example.trade_game.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.common.Montserrat
import com.example.trade_game.domain.view.MarketViewModel
import com.jaikeerthick.composable_graphs.composables.line.LineGraph
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphColors
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphFillType
import com.jaikeerthick.composable_graphs.composables.line.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LabelPosition
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun StockScreen(
    assetId: Int,
    navController: NavController,
    isGestureNavigation: Boolean,
    viewModel: MarketViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val marketHistory by viewModel.marketHistory.collectAsState()

    var info by remember { mutableStateOf("") }
    val paddingText = if (info == "") 0.dp else 10.dp

    LaunchedEffect(Unit) {
        viewModel.getPriceHistory(assetId)
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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

                Text(
                    text = info,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(
                            Brush.horizontalGradient(listOf(
                                Color(0xFF7E9AFF), Color(0xFF1641B7))
                            ),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(paddingText)
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    LineGraph(
                        modifier = Modifier
                            .width(20000.dp)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        data = chartData,
                        onPointClick = { point ->
                            info = "$${point.y}"
                        },
                        style = LineGraphStyle(
                            colors = LineGraphColors(
                                pointColor = Color(0xFF1641B7),
                                lineColor = Color(0xFF1641B7),
                                fillType = LineGraphFillType.Gradient(brush = Brush.verticalGradient(
                                    listOf(Color(0xFF1641B7), Color.White))),
                                clickHighlightColor = Color(0x771641B7),
                                yAxisTextColor = Color.Black
                            ),
                            yAxisLabelPosition = LabelPosition.LEFT
                        )
                    )
                }
            }
        }
    }
}