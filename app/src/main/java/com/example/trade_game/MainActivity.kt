package com.example.trade_game

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.data.UserData
import com.example.trade_game.domain.view.AuthViewModel
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Main(viewModel: AuthViewModel = viewModel()) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val scope = rememberCoroutineScope()
    var user by remember { mutableStateOf<UserData?>(null) }
    LaunchedEffect(Unit) {
        user = preferencesManager.getUserData()
    }

    val navBackStackEntry: NavBackStackEntry? = navController.currentBackStackEntryAsState().value
    val currentRoute = navBackStackEntry?.destination?.route

    val isGestureNavigation = isGestureNavigation()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.refreshToken(preferencesManager)
        }
    }
    Trade_gameTheme {

        val startDestination = if (user != null) "MainScreen" else "LoginScreen"

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                if (currentRoute in listOf(
                        "MainScreen",
                        "EventsScreen",
                        "MarketScreen",
                        "ChatsScreen"
                    )
                ) {
                    Box(
                        modifier = Modifier.padding(bottom = if (isGestureNavigation) 0.dp else 28.dp)
                    ) {
                        BottomBar(navController = navController)
                    }
                }
            }
        ) {
            AppNavigation(navController, startDestination, isGestureNavigation)
        }
    }

}

@Composable
fun isGestureNavigation(): Boolean {
    val navigationBarsHeight =
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    return navigationBarsHeight == 0.dp
}