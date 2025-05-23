package com.example.trade_game.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.trade_game.data.UserData
import com.example.trade_game.domain.view.UserViewModel
import com.example.trade_game.presenter.components.RatingCard
import com.example.trade_game.presenter.components.RatingUserCard
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TopUsersScreen(navController: NavController, isGestureNavigation: Boolean, viewModel: UserViewModel = viewModel()) {
    val usersTop by viewModel.usersTop.collectAsState()
    val userPlace by viewModel.userPlace.collectAsState()
    val userInfo by viewModel.userInfo.collectAsState()

    val ratingPadding = if (isGestureNavigation) 0.dp else 20.dp

    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    var user by remember { mutableStateOf<UserData?>(null) }

    LaunchedEffect(Unit) {
        user = preferencesManager.getUserData()
        viewModel.getUsersTop(100)
        viewModel.getUserInfo(user!!.id)
        viewModel.userInfo.collectLatest { userInfo ->
            userInfo?.data?.id?.let { userId ->
                viewModel.getUserPlace(userId)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 30.dp)
                .padding(bottom = ratingPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Рейтинг",
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    IconButton(
                        modifier = Modifier.align(Alignment.CenterStart),
                        onClick = {
                        navController.navigate("MainScreen")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.cross),
                            contentDescription = "Выйти",
                            tint = MaterialTheme.colorScheme.primary

                        )
                    }
                }
                usersTop?.data?.let { userList ->
                    LazyColumn (
                        modifier = Modifier.fillMaxHeight(0.89f)
                    ) {
                        item{
                            Spacer(Modifier.height(20.dp))
                        }
                        itemsIndexed(userList) { index, user ->
                            RatingCard(user, index + 1, navController)
                        }
                    }
                }
            }
            userInfo?.data?.let  { info ->
                userPlace?.data?.let { place ->
                    RatingUserCard(info.username, place.total_balance, place.place)
                }
            }
        }
    }
}