package com.example.trade_game.presenter

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.R
import com.example.trade_game.common.HeliosExtC
import com.example.trade_game.common.Montserrat
import com.example.trade_game.domain.view.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun RecoverScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val limit = 30
    var email by remember { mutableStateOf("") }
    var popUpActive by remember { mutableStateOf(false) }
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
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(topStart = 100.dp)
                    )
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(50.dp))
                Text(
                    text = "ВОССТАНОВЛЕНИЕ",
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
                            .border(1.dp, Color(0xFF2A41DA), RoundedCornerShape(40.dp))
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
                            onValueChange = { email = it.take(limit) },
                            textStyle = TextStyle(fontSize = 16.sp, color = Color(0xFF2A41DA)),
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 1,
                            singleLine = true
                        )
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
                            if (email.isNotEmpty()) {
                                viewModel.recoverSend(email, navController)
                            }
                        }
                    }
                ) {
                    Text(
                        text = "отправить",
                        fontSize = 20.sp,
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
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}