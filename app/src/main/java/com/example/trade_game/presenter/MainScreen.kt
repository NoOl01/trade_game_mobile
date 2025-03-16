package com.example.trade_game.presenter

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.data.web_sockets.WebSocketClient
import com.example.trade_game.data.web_sockets.WebSocketListenerClient
import com.example.trade_game.domain.view.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun MainView(navController: NavController, viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val scope = rememberCoroutineScope()
    val userDataState = preferencesManager.getUserData.collectAsState(initial = arrayOf("", "", "", ""))
    val token = userDataState.value!![2]

    val wsManager = remember { WebSocketClient() }
    val wsListener = remember { WebSocketListenerClient() }

    var message by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.refreshToken(preferencesManager)
    }

    LaunchedEffect(token) {
        Log.d("tokenM", "Bearer ${userDataState.value!![2]}")
        wsManager.connect("wss://k42647am6339.share.zrok.io/api/v1/chat/private", wsListener, userDataState.value!![2])
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Column (
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = message,
                    onValueChange = {message = it}
                )

                Button(onClick = {
                    val jsonBody = JSONObject().apply {
                        put("recipient_id", 2)
                        put("text", message)
                    }.toString()

                    wsManager.sendMessage(jsonBody)
                }) {
                    Text("Send")
                }
            }
            Spacer(Modifier.height(30.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                scope.launch {
                    preferencesManager.deleteUserData()
                    navController.navigate("LoginScreen")
                }
            }) {
                Text(
                    "Выйти",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}