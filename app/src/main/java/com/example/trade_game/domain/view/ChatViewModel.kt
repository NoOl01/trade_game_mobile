package com.example.trade_game.domain.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.RetrofitInstance
import com.example.trade_game.domain.models.ChatHistoryResponse
import com.example.trade_game.domain.models.ChatsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _chatList = MutableStateFlow<ChatsResponse?>(null)
    val chatList: StateFlow<ChatsResponse?> = _chatList.asStateFlow()

    private val _chatHistory = MutableStateFlow<ChatHistoryResponse?>(null)
    val chatHistory: StateFlow<ChatHistoryResponse?> = _chatHistory.asStateFlow()

    fun getChatsList(preferencesManager: PreferencesManager){
        viewModelScope.launch {
            try {
                val token = preferencesManager.getUserData.first()?.get(3)
                if (token!!.isNotBlank()) {
                    val result = RetrofitInstance.apiService.getChatsList("Bearer $token")
                    _chatList.value = result
                }
            } catch (ex: Exception) {
                _chatList.value = ChatsResponse("Error", emptyList(), ex.localizedMessage)
            }
        }
    }

    fun getChatHistory(preferencesManager: PreferencesManager, userId: Int, beforeMessageId: Int, limit: Int){
        viewModelScope.launch {
            try {
                val token = preferencesManager.getUserData.first()?.get(3)
                if (token!!.isNotBlank()) {
                    val result = RetrofitInstance.apiService.getChatHistory("Bearer $token", userId, beforeMessageId, limit)
                    _chatHistory.value = result
                }
            } catch (ex: Exception) {
                _chatHistory.value = ChatHistoryResponse("Error", emptyList(), ex.localizedMessage)
            }
        }
    }
}