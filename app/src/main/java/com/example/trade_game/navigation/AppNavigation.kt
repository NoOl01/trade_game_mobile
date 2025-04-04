package com.example.trade_game.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.trade_game.presenter.ChatsScreen
import com.example.trade_game.presenter.EventsScreen
import com.example.trade_game.presenter.LoginView
import com.example.trade_game.presenter.MainView
import com.example.trade_game.presenter.MarketScreen
import com.example.trade_game.presenter.PrivateChatScreen
import com.example.trade_game.presenter.RegisterScreen
import com.example.trade_game.presenter.StockScreen
import com.example.trade_game.presenter.TopUsersScreen
import com.example.trade_game.presenter.ProfileScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    isGestureNavigation: Boolean
) {
    NavHost(
        navController, startDestination = startDestination,
        enterTransition = { fadeIn(animationSpec = tween(0)) },
        exitTransition = { fadeOut(animationSpec = tween(0)) },
        popEnterTransition = { fadeIn(animationSpec = tween(0)) },
        popExitTransition = { fadeOut(animationSpec = tween(0)) }) {
        composable("MainScreen") { MainView(navController, isGestureNavigation) }
        composable("EventsScreen") { EventsScreen(navController, isGestureNavigation) }
        composable("ChatsScreen") { ChatsScreen(navController, isGestureNavigation) }
        composable("MarketScreen") { MarketScreen(navController, isGestureNavigation) }
        composable("TopUsersScreen") { TopUsersScreen(navController, isGestureNavigation) }
        composable("LoginScreen") { LoginView(navController) }
        composable("RegisterScreen") { RegisterScreen(navController) }
        composable(
            "Chat/{userId}/{userName}", arguments =
                listOf(
                    navArgument("userId") { type = NavType.IntType },
                    navArgument("userName") { type = NavType.StringType })
        ) { stackEntry ->
            val userId = stackEntry.arguments?.getInt("userId")
            val userName = stackEntry.arguments?.getString("userName")
            PrivateChatScreen(userId!!, userName!!, navController, isGestureNavigation)
        }
        composable(
            "StockScreen/{assetId}/{assetName}", arguments =
            listOf(navArgument("assetId") { type = NavType.IntType },
                navArgument("assetName") {type = NavType.StringType})) { stackEntry ->
            val assetId = stackEntry.arguments?.getInt("assetId")
            val assetName = stackEntry.arguments?.getString("assetName")
            StockScreen(assetId!!, assetName!!, navController, isGestureNavigation)
        }
        composable(
            "Profile/{userId}", arguments =
            listOf(navArgument("userId") { type = NavType.IntType })) { stackEntry ->
            val userId = stackEntry.arguments?.getInt("userId")
            ProfileScreen(userId!!, navController, isGestureNavigation)
        }
    }
}