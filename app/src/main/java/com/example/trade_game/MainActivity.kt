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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.view.MainViewModel
import com.example.trade_game.navigation.AppNavigation
import com.example.trade_game.presenter.components.BottomBar
import com.example.trade_game.ui.theme.Trade_gameTheme
import kotlinx.coroutines.launch

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
fun Main(viewModel: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val scope = rememberCoroutineScope()

    val accessTokenState =
        preferencesManager.getUserData.collectAsState(initial = arrayOf("", "", "", ""))

    val isDataLoaded = !accessTokenState.value.contentEquals(arrayOf("", "", "", ""))
    val navBackStackEntry: NavBackStackEntry? = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.refreshToken(preferencesManager)
        }
    }
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
                    if (currentRoute == "MainScreen" || currentRoute == "EventsScreen" || currentRoute == "MarketScreen" || currentRoute == "ChatsScreen") {
                        BottomBar(navController = navController)
                    }
                }) { innerPadding ->
                AppNavigation(navController, startDestination, innerPadding)
            }

        }
    }
}
