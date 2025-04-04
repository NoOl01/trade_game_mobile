package com.example.trade_game.presenter

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.trade_game.domain.view.UserViewModel
import com.example.trade_game.presenter.components.UserAssetCard
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun MainView(
    navController: NavController,
    isGestureNavigation: Boolean,
    viewModel: UserViewModel = viewModel()
) {
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val scope = rememberCoroutineScope()
    val userInfo by viewModel.userInfo.collectAsState()
    val userPlace by viewModel.userPlace.collectAsState()
    val userAssets by viewModel.usersAssets.collectAsState()

    val padding = if (isGestureNavigation) 0.dp else 70.dp
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getUserInfo(preferencesManager.getUserData.first()?.get(0)!!.toInt())
            viewModel.getUserAssets(preferencesManager.getUserData.first()?.get(0)!!.toInt())
            viewModel.userInfo.collectLatest { userInfo ->
                userInfo?.data?.id?.let { userId ->
                    viewModel.getUserPlace(userId)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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
                onClick = {}
            ) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Настройки",
                    tint = Color(0xFF1641B7)
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
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Место #${userPlace?.data?.place ?: "0"}",
                        color = Color(0xFF1641B7),
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
                            .background(Color(0xFF1641B7))

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
                        .background(Color(0xFF1641B7)),
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
                            color = Color.White
                        )
                        Text(
                            text = "$${userInfo?.data?.balance ?: "0"}",
                            fontFamily = Montserrat,
                            fontSize = 30.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(Modifier.height(20.dp))
            userAssets?.data?.let { assets ->
                LazyColumn {
                    items(assets) { asset ->
                        UserAssetCard(asset, navController)
                    }
                    item {
                        Spacer(Modifier.height( 60.dp + padding))
                    }
                }
            }
        }
    }
}