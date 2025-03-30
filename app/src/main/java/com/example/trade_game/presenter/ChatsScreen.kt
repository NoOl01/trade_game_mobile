package com.example.trade_game.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.common.Montserrat
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.view.ChatViewModel

@Composable
fun ChatsScreen(
    navController: NavController,
    isGestureNavigation: Boolean,
    viewModel: ChatViewModel = viewModel()
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    val chatList by viewModel.chatList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getChatsList(preferencesManager)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(top = 34.dp)
        ) {
            chatList?.data?.let { data ->
                if (data.isNotEmpty()) {
                    LazyColumn {

                    }
                } else {
                    Column (
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Сейчас тут пусто",
                            fontSize = 30.sp,
                            fontFamily = Montserrat
                        )
                    }
                }
            }
        }
    }
}