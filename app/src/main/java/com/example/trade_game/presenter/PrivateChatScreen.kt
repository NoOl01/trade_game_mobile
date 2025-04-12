package com.example.trade_game.presenter

import android.util.Log
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
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
import com.example.trade_game.common.formatTime
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.BASE_URL
import com.example.trade_game.domain.models.ChatHistoryData
import com.example.trade_game.domain.view.ChatViewModel
import com.example.trade_game.domain.web_sockets.WebSocketManager
import com.example.trade_game.ui.theme.Primary
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar


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
    val webSocketManager = remember { WebSocketManager("wss://${BASE_URL}/api/v1/chat/private") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val selfUserId = runBlocking { preferencesManager.getUserData.first()?.get(0)!!.toInt() }
    val accessToken = runBlocking { preferencesManager.getUserData.first()?.get(3)!! }
    LaunchedEffect(Unit) {
        viewModel.getChatHistory(
            preferencesManager = preferencesManager,
            userId = userId,
            beforeMessageId = null,
            limit = 50
        )
        webSocketManager.connect(accessToken) { jsonMessage ->
            try {
                val jsonObject = JSONObject(jsonMessage)

                viewModel.addMessage(
                    ChatHistoryData(
                        message_id = jsonObject.getInt("message_id"),
                        from_id = jsonObject.getInt("from_id"),
                        recipient_id = jsonObject.getInt("recipient_id"),
                        text = jsonObject.getString("text"),
                        created_at = jsonObject.getString("created_at"),
                    )
                )

            } catch (e: Exception) {
                Log.e("WebSocket", "Error parsing JSON", e)
            }
        }
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
                    .padding(bottom = 10.dp),
                state = listState,
                reverseLayout = true
            ) {
                items(messagesList!!.data.reversed()) { msg ->
                    MessageItem(msg, selfUserId)
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
                .padding(horizontal = 10.dp),
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
                            webSocketManager.sendMessage(userId, messageText)
                            viewModel.addMessage(
                                ChatHistoryData(
                                    message_id = -1,
                                    from_id = selfUserId,
                                    recipient_id = userId,
                                    text = messageText,
                                    created_at = getCurrentTimestamp()
                                )
                            )

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
                    unfocusedBorderColor = Primary,
                )
            )

            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        webSocketManager.sendMessage(userId, messageText)
                        viewModel.addMessage(
                            ChatHistoryData(
                                message_id = if (messagesList?.data?.size != 0) messagesList?.data?.last()?.message_id?.plus(1) ?: 0 else 0,
                                from_id = selfUserId,
                                recipient_id = userId,
                                text = messageText,
                                created_at = getCurrentTimestamp()
                            )
                        )

                        messageText = ""
                        keyboardController?.hide()
                    }
                },
                modifier = Modifier
                    .size(50.dp)
                    .border(2.dp, Primary, shape = CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.send),
                    contentDescription = "Отправить",
                    tint = Primary,
                    modifier = Modifier
                        .size(36.dp)
                        .graphicsLayer(
                            rotationZ = -45f
                        )
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
fun MessageItem(message: ChatHistoryData, selfId: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (selfId == message.recipient_id) Arrangement.Start else Arrangement.End
    )
    {
        Column(
            modifier = Modifier
                .background(Primary, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
                .widthIn(min = 50.dp, max = 300.dp)
        ) {
            Text(message.text, color = Color.White, fontSize = 18.sp)
            Text(
                text = formatTime(message.created_at),
                textAlign = TextAlign.Right,
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }

}

fun getCurrentTimestamp(): String {
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    return now.format(formatter)
}