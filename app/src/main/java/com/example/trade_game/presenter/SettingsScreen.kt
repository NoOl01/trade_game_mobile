package com.example.trade_game.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.ui.theme.Primary
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    isGestureNavigation: Boolean,
) {
    val padding = if (isGestureNavigation) 0.dp else 50.dp
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(vertical = padding)
    ) {
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(50.dp),
            colors = ButtonColors(
                containerColor = Primary,
                contentColor = Color.White,
                disabledContainerColor = Primary,
                disabledContentColor = Color.White
            ),
            onClick = {
                scope.launch {
                    preferencesManager.deleteUserData()
                }
            }
        ) {
            Text(
                text = "Выйти",
                color = Color.White
            )
        }
    }
}