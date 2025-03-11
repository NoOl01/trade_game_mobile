package com.example.trade_game.presenter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.view.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun MainView(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val scope = rememberCoroutineScope()
    val userDataState = preferencesManager.getUserData.collectAsState(initial = arrayOf("", "", "", ""))

    LaunchedEffect(Unit) {
        viewModel.refreshToken(preferencesManager)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = "Email: ${userDataState.value!![0]}",
                fontSize = 20.sp
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text = "Name: ${userDataState.value!![1]}",
                fontSize = 20.sp
            )
            Spacer(Modifier.height(30.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                scope.launch {
                    preferencesManager.deleteUserData()
                    navController.navigate("LoginScreen")
                }
            }) {
                Text(
                    "Выйти",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}