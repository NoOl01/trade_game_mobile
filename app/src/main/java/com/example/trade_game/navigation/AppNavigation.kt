package com.example.trade_game.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trade_game.presenter.ChatsScreen
import com.example.trade_game.presenter.EventsScreen
import com.example.trade_game.presenter.LoginView
import com.example.trade_game.presenter.MainView
import com.example.trade_game.presenter.MarketScreen
import com.example.trade_game.presenter.RegisterScreen
import com.example.trade_game.presenter.StockScreen

@Composable
fun AppNavigation(navController: NavHostController, startDestination: String, padding: PaddingValues) {
    NavHost(
        navController, startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) }) {
        composable("MainScreen") { MainView(navController) }
        composable("EventsScreen") { EventsScreen(navController) }
        composable("ChatsScreen") { ChatsScreen(navController) }
        composable("MarketScreen") { MarketScreen(navController) }
        composable("StockScreen") { StockScreen(navController) }
        composable("LoginScreen") { LoginView(navController, padding) }
        composable("RegisterScreen") { RegisterScreen(navController, padding) }
    }
}