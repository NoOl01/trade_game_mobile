package com.example.trade_game.presenter

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.view.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginView(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val limit = 30
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val scope = rememberCoroutineScope()
    val response by viewModel.auth.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Войдите в аккаунт",
                fontSize = 30.sp,
                modifier = Modifier.padding(top = 150.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it.take(limit) },
                    label = { Text("Email/Nickname") },
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
                OutlinedButton(onClick = {
                    scope.launch {
                        if (name.isNotEmpty() && password.isNotEmpty()) {
                            viewModel.login(name, password)
                        }
                        if (response != null){
                            if (response?.error == null){
                                preferencesManager.setAccessToken(response!!.data!!.access_token)
                            }
                        }
                    }

                }) {
                    Text("Войти", fontSize = 20.sp)
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Еще нет аккаунта?")
                TextButton(onClick = { navController.navigate("RegisterScreen") }) {
                    Text("Зарегистрироваться")
                }
            }
        }
    }
}