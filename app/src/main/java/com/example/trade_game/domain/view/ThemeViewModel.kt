package com.example.trade_game.domain.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trade_game.data.ThemePreference
import com.example.trade_game.data.ThemePreference.themeFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ThemeViewModel : ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    fun loadTheme(context: Context) {
        viewModelScope.launch {
            context.themeFlow.collect {
                _isDarkTheme.value = it
            }
        }
    }

    fun toggleTheme(context: Context) {
        viewModelScope.launch {
            val newTheme = !_isDarkTheme.value
            ThemePreference.saveTheme(context, newTheme)
        }
    }
}
