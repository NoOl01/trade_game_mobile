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
import com.example.trade_game.presenter.RegisterScreen

@Composable
fun AppNavigation(navController: NavHostController, startDestination: String, padding: PaddingValues) {
    NavHost(
        navController, startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) }) {
        composable("MainScreen") { MainView(navController, padding) }
        composable("EventsScreen") { EventsScreen(navController, padding) }
        composable("ChatsScreen") { ChatsScreen(navController, padding) }
        composable("LoginScreen") { LoginView(navController, padding) }
        composable("RegisterScreen") { RegisterScreen(navController, padding) }
    }
}