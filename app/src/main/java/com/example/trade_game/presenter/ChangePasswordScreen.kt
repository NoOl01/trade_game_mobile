package com.example.trade_game.presenter

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.R
import com.example.trade_game.common.Montserrat
import com.example.trade_game.domain.view.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ChangePasswordScreen(
    jwt: String,
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val limit = 30
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordRepeatVisible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .border(1.dp, Color(0xFF2A41DA), RoundedCornerShape(40.dp))
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (password.isEmpty()) {
                        Text(
                            text = "пароль",
                            color = Color(0xB0364CDF),
                            fontSize = 16.sp,
                            fontFamily = Montserrat
                        )
                    }
                    BasicTextField(
                        value = password,
                        onValueChange = { password = it.take(limit) },
                        textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF2A41DA)),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        singleLine = true
                    )
                }
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(if (passwordVisible) R.drawable.pass_on else R.drawable.pass_off),
                        contentDescription = "Показать/скрыть пароль",
                        tint = Color(0xFF2A41DA)
                    )
                }
            }
        }
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
        ) {
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .border(1.dp, Color(0xFF2A41DA), RoundedCornerShape(40.dp))
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (passwordRepeat.isEmpty()) {
                        Text(
                            text = "повторите пароль",
                            color = Color(0xB0364CDF),
                            fontSize = 16.sp,
                            fontFamily = Montserrat
                        )
                    }
                    BasicTextField(
                        value = passwordRepeat,
                        onValueChange = { passwordRepeat = it.take(limit) },
                        textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF2A41DA)),
                        visualTransformation = if (passwordRepeatVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        singleLine = true
                    )
                }
                IconButton(onClick = { passwordRepeatVisible = !passwordRepeatVisible }) {
                    Icon(
                        painter = painterResource(if (passwordRepeatVisible) R.drawable.pass_on else R.drawable.pass_off),
                        contentDescription = "Показать/скрыть пароль",
                        tint = Color(0xFF2A41DA)
                    )
                }
            }
        }
        Spacer(Modifier.height(40.dp))
        Button(
            colors = ButtonColors(
                containerColor = Color(0xFF2A41DA),
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color(0xFF2A41DA),
                disabledContentColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp)
                .shadow(
                    elevation = 5.dp,
                    shape = RoundedCornerShape(50),
                    clip = false
                )
                .clip(RoundedCornerShape(50)),
            onClick = {
                scope.launch {
                    if (password.isNotEmpty() && passwordRepeat.isNotEmpty()) {
                        viewModel.changePassword(password, jwt, navController)
                    }
                }
            }
        ) {
            Text(
                text = "Подтвердить",
                fontSize = 20.sp,
                fontFamily = Montserrat
            )
        }
    }
}