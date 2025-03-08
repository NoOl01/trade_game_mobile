package com.example.trade_game.presenter

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trade_game.common.isValidEmail
import com.example.trade_game.data.PreferencesManager
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {
    val limit = 30

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    val validFocusedTextFieldColor by animateColorAsState(
        if (isValid) Color(0xFF55AEBB) else Color(0xFF9D0220)
    )
    val validUnfocusedTextFieldColor by animateColorAsState(if (isValid) Color.Gray else Color.Red)

    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.padding(top = 150.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Регистрация",
                    fontSize = 30.sp
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        isValid = isValidEmail(it.take(limit))
                        email = it.take(limit)
                    },
                    label = { Text("Почта") },
                    shape = RoundedCornerShape(10.dp),
                    maxLines = 1,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = validFocusedTextFieldColor,
                        unfocusedIndicatorColor = validUnfocusedTextFieldColor,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        errorContainerColor = Color.Transparent
                    )
                )
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it.take(limit) },
                    label = { Text("Никнейм") },
                    shape = RoundedCornerShape(10.dp),
                    maxLines = 1,
                )
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it.take(limit) },
                    label = { Text("Пароль") },
                    shape = RoundedCornerShape(10.dp)
                )
                Spacer(Modifier.height(20.dp))
                OutlinedButton(onClick = {}) {
                    Text("Войти", fontSize = 20.sp)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Уже есть аккаунт?")
                TextButton(onClick = {
                    scope.launch {
                        navController.navigate("LoginScreen")
                    }
                }) {
                    Text("войти")
                }
            }
        }
    }
}