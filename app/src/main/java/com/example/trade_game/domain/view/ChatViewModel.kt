package com.example.trade_game.domain.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.RetrofitInstance
import com.example.trade_game.domain.models.ChatsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _chatList = MutableStateFlow<ChatsResponse?>(null)
    val chatList: StateFlow<ChatsResponse?> = _chatList.asStateFlow()

    fun getChatsList(preferencesManager: PreferencesManager){
        viewModelScope.launch {
            try {
                val token = preferencesManager.getUserData.first()?.get(2)
                if (token!!.isNotBlank()) {
                    val result = RetrofitInstance.apiService.getChatsList("Bearer $token")
                    _chatList.value = result
                }
            } catch (ex: Exception) {
                _chatList.value = ChatsResponse("Error", emptyList(), ex.localizedMessage)
            }
        }
    }
}