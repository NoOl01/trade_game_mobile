package com.example.trade_game.presenter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.R
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
    val interactionSource = remember { MutableInteractionSource() }
    val ratingPadding = if (isGestureNavigation) 0.dp else 30.dp
    val chatList by viewModel.chatList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getChatsList(preferencesManager)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(vertical = ratingPadding)
        ) {
            chatList?.data?.let { data ->
                if (data.isNotEmpty()) {
                    LazyColumn {
                        items(data) { user ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp)
                                    .border(
                                        width = 2.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(10.dp)
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null
                                    ) {
                                        navController.navigate("Chat/${user.id}/${user.username}")
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.user),
                                    contentDescription = "Аватарка"
                                )
                                Spacer(modifier = Modifier.width(30.dp))
                                Text(
                                    text = user.username,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                } else {
                    Column(
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