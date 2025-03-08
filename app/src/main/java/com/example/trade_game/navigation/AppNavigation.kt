package com.example.trade_game.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.trade_game.presenter.LoginView
import com.example.trade_game.presenter.MainView
import com.example.trade_game.presenter.RegisterScreen

@Composable
fun AppNavigation(navController: NavHostController, startDestination: String){
    NavHost(navController, startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) }){
        composable("MainScreen") { MainView(navController) }
        composable("LoginScreen") { LoginView(navController) }
        composable("RegisterScreen") { RegisterScreen(navController) }
    }
}