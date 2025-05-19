package com.example.trade_game.presenter

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.R
import com.example.trade_game.common.Montserrat
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.data.UserData
import com.example.trade_game.domain.view.UserViewModel
import com.example.trade_game.presenter.components.UserAssetCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainView(
    navController: NavController,
    isGestureNavigation: Boolean,
    viewModel: UserViewModel = viewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val userInfo by viewModel.userInfo.collectAsState()
    val userPlace by viewModel.userPlace.collectAsState()
    val userAssets by viewModel.usersAssets.collectAsState()
    var user by remember { mutableStateOf<UserData?>(null) }
    val padding = if (isGestureNavigation) 0.dp else 70.dp
    LaunchedEffect(Unit) {
        val loadedUser = preferencesManager.getUserData()
        user = loadedUser

        loadedUser?.let { u ->
            viewModel.getUserInfo(u.id)
            viewModel.getUserAssets(u.id)
        }

        viewModel.userInfo.collectLatest { info ->
            info?.data?.id?.let { viewModel.getUserPlace(it) }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(top = 24.dp)
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    navController.navigate("SettingsScreen")
                }
            ) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Настройки",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "UserIcon"
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text(
                        text = userInfo?.data?.username ?: "Загрузка...",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 20.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Место #${userPlace?.data?.place ?: "0"}",
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = Montserrat,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                navController.navigate("TopUsersScreen")
                            }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(horizontal = 24.dp)

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(25.dp)
                        .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                        .background(Color(0xFF89A9FF))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(topEnd = 15.dp))
                            .background(MaterialTheme.colorScheme.primary)

                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF89A9FF))
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 20.dp,
                                bottomEnd = 20.dp,
                                topEnd = 20.dp
                            )
                        )
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ваш баланс",
                            fontFamily = Montserrat,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            text = "$${userInfo?.data?.balance ?: "0"}",
                            fontFamily = Montserrat,
                            fontSize = 30.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            when {
                userAssets != null && userAssets!!.data.isNotEmpty() -> {
                    userAssets?.data?.let { assets ->
                        LazyColumn {
                            items(assets) { asset ->
                                UserAssetCard(asset, navController)
                            }
                            item {
                                Spacer(Modifier.height(60.dp + padding))
                            }
                        }
                    }
                }

                userAssets == null || userAssets!!.data.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(200.dp),
                            colors = CardColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.background,
                                disabledContentColor = MaterialTheme.colorScheme.primary
                            ),
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "У вас пока что\n нет акций :(",
                                    textAlign = TextAlign.Center,
                                    fontSize = 24.sp

                                )
                                Spacer(Modifier.height(20.dp))
                                ElevatedButton(
                                    colors = ButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primary,
                                        contentColor = MaterialTheme.colorScheme.onPrimary,
                                        disabledContainerColor = MaterialTheme.colorScheme.primary,
                                        disabledContentColor = MaterialTheme.colorScheme.onPrimary
                                    ),
                                    onClick = { navController.navigate("MarketScreen") }
                                ) {
                                    Text(text = "Купить")
                                }
                            }
                        }
                    }

                }

            }

        }
    }
}