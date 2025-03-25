package com.example.trade_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.navigation.AppNavigation
import com.example.trade_game.presenter.BottomBar
import com.example.trade_game.ui.theme.Trade_gameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Main()
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    val accessTokenState =
        preferencesManager.getUserData.collectAsState(initial = arrayOf("", "", "", ""))

    val isDataLoaded = !accessTokenState.value.contentEquals(arrayOf("", "", "", ""))
    Trade_gameTheme {
        if (!isDataLoaded) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val accessToken = accessTokenState.value
            val startDestination =
                if (accessToken!![2].isNotBlank()) "MainScreen" else "LoginScreen"

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    if (startDestination == "MainScreen") {
                        BottomBar(navController = navController)
                    }
                }) { innerPadding ->
                AppNavigation(navController, startDestination, innerPadding)
            }

        }

    }
}
