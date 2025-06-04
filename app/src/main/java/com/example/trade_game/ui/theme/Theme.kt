package com.example.trade_game.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.trade_game.data.ThemePreference.themeFlow

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    secondary = Color(0xFF1A1A1A),
    onSecondary = Color.White,
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF121212),
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    secondary = Color.White,
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

@Composable
fun Trade_gameTheme(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val isDarkTheme by produceState(initialValue = isSystemInDarkTheme(), context) {
        context.themeFlow.collect {
            value = it
        }
    }

    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}