package com.example.trade_game.domain.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trade_game.domain.RetrofitInstance
import com.example.trade_game.domain.models.EventsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EventsViewModel : ViewModel(){
    private val _events = MutableStateFlow<EventsResponse?>(null)
    val events: StateFlow<EventsResponse?> = _events.asStateFlow()

    fun getEvents() {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.apiService.events()
                _events.value = result
            } catch (ex: Exception) {
                _events.value = EventsResponse("Error", emptyList(), ex.localizedMessage)
            }
        }
    }
}