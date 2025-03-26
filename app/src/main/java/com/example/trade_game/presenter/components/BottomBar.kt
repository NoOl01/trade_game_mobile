package com.example.trade_game.presenter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.trade_game.R
import com.example.trade_game.domain.view.MainViewModel

@Composable
fun BottomBar(navController: NavController, viewModel: MainViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        val navBackStackEntry: NavBackStackEntry? = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route
        println(currentRoute)
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(
                    bottom = 28.dp, start = 8.dp, top = 8.dp, end = 8.dp
                )
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(
                onClick = {
                    navController.navigate("MainScreen")
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.market),
                    contentDescription = "Магазин",
                    tint = if (currentRoute == "MainScreen") Color(0xFF2A41DA) else Color(0xFF7F7F7F),
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
            IconButton(
                onClick = {
                    navController.navigate("EventsScreen")
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.events),
                    contentDescription = "События",
                    tint = if (currentRoute == "EventsScreen") Color(0xFF2A41DA) else Color(0xFF7F7F7F),
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
            IconButton(
                onClick = {
                    navController.navigate("MainScreen")
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.purchases),
                    contentDescription = "Покупки",
                    tint = if (currentRoute == "MainScreen") Color(0xFF2A41DA) else Color(0xFF7F7F7F),
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
            IconButton(
                onClick = {
                    navController.navigate("ChatsScreen")
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.messages),
                    contentDescription = "Сообщения",
                    tint = if (currentRoute == "ChatsScreen") Color(0xFF2A41DA) else Color(0xFF7F7F7F),
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }

        }
    }

}

