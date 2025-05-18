package com.example.trade_game.presenter


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.domain.view.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OtpVerificationScreen(
    email: String,
    navController: NavController,
) {
    val code = remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val isCodeComplete = code.value.length == 6

    val viewModel: AuthViewModel = viewModel()
    val focusRequester = remember { FocusRequester() }

    val resendEnabled = remember { mutableStateOf(false) }
    var timer by remember { mutableIntStateOf(60) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (timer > 0) {
            delay(1000)
            timer--
        }
        resendEnabled.value = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Введите код", fontSize = 24.sp, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Код отправлен на $email", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = code.value,
            onValueChange = {
                if (it.length <= 6 && it.all { ch -> ch.isDigit() }) {
                    code.value = it
                    if (it.length == 6) focusManager.clearFocus()
                }
            },
            modifier = Modifier
                .width(1.dp)
                .height(0.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            textStyle = TextStyle(fontSize = 0.sp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                cursorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (0 until 6).forEach { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .border(
                            BorderStroke(
                                1.dp,
                                if (code.value.length == index) MaterialTheme.colorScheme.primary else Color.Gray
                            ),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable {
                            focusRequester.requestFocus()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = code.value.getOrNull(index)?.toString() ?: "",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.recoverCheck(email, code.value.toInt(), navController)
            },

            enabled = isCodeComplete,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isCodeComplete) Color(0xFF2A41DA) else Color(0xFFCCCCCC),
                contentColor = Color.White
            )
        ) {
            Text("Проверить")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (resendEnabled.value) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "Отправить заново",
                    color = Color(0xFF2A41DA),
                    modifier = Modifier.clickable {
                        viewModel.recoverSend(email, navController)
                        timer = 60
                        resendEnabled.value = false
                        coroutineScope.launch {
                            while (timer > 0) {
                                delay(1000)
                                timer--
                            }
                            resendEnabled.value = true
                        }
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text("Вы можете запросить новый код через $timer секунд", color = Color.Gray, fontSize = 12.sp)
            }
        } else {
            Text("Вы можете запросить новый код через $timer секунд", color = Color.Gray)
        }
    }
}

