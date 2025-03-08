package com.example.trade_game.domain.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trade_game.domain.RetrofitInstance
import com.example.trade_game.domain.models.LoginRequest
import com.example.trade_game.domain.models.RegisterRequest
import com.example.trade_game.domain.models.RegisterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _register = MutableStateFlow<RegisterResponse?>(null)
    val register: StateFlow<RegisterResponse?> = _register

    private val _login = MutableStateFlow(RegisterResponse("", emptyList(), null))
    val login: StateFlow<RegisterResponse> = _login

    fun register(email: String, name: String, password: String){
        viewModelScope.launch {
            try {
                val newReg = RegisterRequest(email, name, password)
                val response = RetrofitInstance.apiService.register(newReg)
                _register.value = response
            } catch (ex: Exception){
                println(ex)
            }
        }
    }

    fun login(username: String, password: String){
        viewModelScope.launch {
            try {
                val newReg = LoginRequest(username, password)
                val response = RetrofitInstance.apiService.login(newReg)
                _login.value = response.copy()
            } catch (ex: Exception){
                println(ex)
            }
        }
    }
}