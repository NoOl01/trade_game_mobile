package com.example.trade_game.presenter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trade_game.R
import com.example.trade_game.common.formatDateTime
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.models.ChatHistoryData
import com.example.trade_game.domain.view.ChatViewModel
import com.example.trade_game.ui.theme.Primary


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivateChatScreen(
    userId: Int,
    userName: String,
    navController: NavController,
    isGestureNavigation: Boolean,
    viewModel: ChatViewModel = viewModel()
) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    val messagesList by viewModel.chatHistory.collectAsState()
    val padding = if (isGestureNavigation) 0.dp else 30.dp
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        viewModel.getChatHistory(
            preferencesManager = preferencesManager,
            userId = userId,
            beforeMessageId = null,
            limit = 50
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = padding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .background(Primary, shape = RoundedCornerShape(15.dp))
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = userName,
                fontSize = 24.sp,
                color = Color.White
            )
        }

        if (messagesList != null && messagesList!!.data.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 56.dp),
                state = listState,
                reverseLayout = true
            ) {
                items(messagesList!!.data.reversed()) { msg ->
                    MessageItem(msg)
                    Spacer(Modifier.height(6.dp))
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Нет сообщений :(",
                    color = Primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .imePadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { newValue -> messageText = newValue },
                placeholder = { Text("Введите сообщение...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (messageText.isNotBlank()) {
                            // TODO: Добавить логику отправки сообщения
                            messageText = ""
                            keyboardController?.hide()
                        }
                    }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Primary,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Color.LightGray,
                )
            )

            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        // TODO: Добавить логику отправки сообщения
                        messageText = ""
                    }
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.send),
                    contentDescription = "Отправить",
                    tint = Primary
                )
            }
        }
    }

    LaunchedEffect(messagesList?.data?.size) {
        if (messagesList?.data?.isNotEmpty() == true) {
            listState.animateScrollToItem(0)
        }
    }
}

@Composable
fun MessageItem(message: ChatHistoryData) {
    Column {
        Text(message.text)
        Text(
            text = formatDateTime(message.created_at),
            textAlign = TextAlign.Right
        )
    }
}