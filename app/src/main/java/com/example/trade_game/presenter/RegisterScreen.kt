package com.example.trade_game.presenter

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.R
import com.example.trade_game.common.HeliosExtC
import com.example.trade_game.common.Montserrat
import com.example.trade_game.common.isValidEmail
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.view.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel = viewModel()) {
    val limit = 30

    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(true) }
    var popUpActive by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordRepeatVisible by remember { mutableStateOf(false) }

    val validFocusedTextFieldColor by animateColorAsState(
        if (isValid) Color(0xFF55AEBB) else Color(0xFF9D0220)
    )

    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val authResult = viewModel.auth.collectAsState()

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Логотип",
                    modifier = Modifier
                        .height(40.dp)
                        .width(40.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    text = "БАНК НН",
                    fontSize = 20.sp,
                    fontFamily = HeliosExtC,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(Modifier.height(40.dp))
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(topStart = 100.dp))
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(50.dp))
                Text(
                    text = "АВТОРИЗАЦИЯ",
                    color = Color(0xFF364CDF),
                    fontSize = 24.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .border(1.dp, validFocusedTextFieldColor, RoundedCornerShape(40.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        if (email.isEmpty()) {
                            Text(
                                text = "почта",
                                color = Color(0xB0364CDF),
                                fontSize = 16.sp,
                                fontFamily = Montserrat
                            )
                        }
                        BasicTextField(
                            value = email,
                            onValueChange = {
                                email = it.take(limit)
                                isValid = isValidEmail(it)
                            },
                            textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF2A41DA)),
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            singleLine = true
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(40.dp)
                            .border(1.dp, Color(0xFF2A41DA), RoundedCornerShape(40.dp))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        if (name.isEmpty()) {
                            Text(
                                text = "логин",
                                color = Color(0xB0364CDF),
                                fontSize = 16.sp,
                                fontFamily = Montserrat
                            )
                        }
                        BasicTextField(
                            value = name,
                            onValueChange = { name = it.take(limit) },
                            textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF2A41DA)),
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            singleLine = true
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
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
                Spacer(Modifier.height(10.dp))
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
                Spacer(Modifier.height(50.dp))
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
                            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && isValid) {
                                viewModel.register(email, name, password, preferencesManager)
                                val authResponse = authResult.value
                                popUpActive = true
                                delay(10000)
                                if (authResponse != null && authResponse.status == "Ok") {
                                    navController.navigate("MainScreen")
                                } else {
                                    popUpActive = false
                                }
                            }
                        }
                    }
                ) {
                    Text(
                        text = "зарегистрироваться",
                        fontSize = 20.sp,
                        fontFamily = Montserrat
                    )
                }
                TextButton(onClick = {
                    navController.navigate("LoginScreen")
                }) {
                    Text(
                        text = "войти",
                        fontSize = 18.sp,
                        color = Color(0xFF2A41DA),
                        fontFamily = Montserrat
                    )
                }
            }
        }
    }
    if (popUpActive) {
        Popup(
            alignment = Alignment.Center,
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF040331)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}