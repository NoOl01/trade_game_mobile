package com.example.trade_game.presenter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trade_game.data.PreferencesManager
import kotlinx.coroutines.launch

@Composable
fun MainView(navController: NavController) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            scope.launch {
                preferencesManager.deleteRegistered()
                navController.navigate("login") {
                    popUpTo("main") { inclusive = true }
                }
            }
        }) {
            Text(
                "Выйти и зайти нормально",
                fontSize = 30.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}