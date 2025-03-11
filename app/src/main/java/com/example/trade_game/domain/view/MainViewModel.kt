package com.example.trade_game.domain.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.RetrofitInstance
import com.example.trade_game.domain.models.AuthResponse
import com.example.trade_game.domain.models.LoginRequest
import com.example.trade_game.domain.models.RefreshRequest
import com.example.trade_game.domain.models.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _auth = MutableStateFlow<AuthResponse?>(null)
    val auth: StateFlow<AuthResponse?> = _auth.asStateFlow()

    fun register(email: String, name: String, password: String, preferencesManager: PreferencesManager) {
        viewModelScope.launch {
            try {
                val newReg = RegisterRequest(email, name, password)
                val response = RetrofitInstance.apiService.register(newReg)
                if (response.data != null && response.status == "Ok"){
                    preferencesManager.saveUserData(
                        response.data.email,
                        response.data.username,
                        response.data.access_token,
                        response.data.refresh_token
                    )
                    _auth.value = response
                }
                else{
                    _auth.value = AuthResponse("error", null, "Invalid credentials")
                }
            } catch (ex: Exception) {
                _auth.value = AuthResponse("error", null, ex.localizedMessage)
            }
        }
    }

    fun login(username: String, password: String, preferencesManager: PreferencesManager) {
        viewModelScope.launch {
            try {
                val newReg = LoginRequest(username, password)
                val response = RetrofitInstance.apiService.login(newReg)

                if (response.data != null && response.status == "Ok") {
                    preferencesManager.saveUserData(
                        response.data.email,
                        response.data.username,
                        response.data.access_token,
                        response.data.refresh_token
                    )
                    _auth.value = response
                } else {
                    _auth.value = AuthResponse("error", null, "Invalid credentials")
                }
            } catch (ex: Exception) {
                _auth.value = AuthResponse("error", null, ex.localizedMessage)
            }
        }
    }

    private suspend fun refresh(refreshToken: String): AuthResponse {
        return try {
            val newRefreshRequest = RefreshRequest(refreshToken)
            RetrofitInstance.apiService.refresh(newRefreshRequest)
        } catch (ex: Exception) {
            AuthResponse("error", null, ex.localizedMessage)
        }
    }

    fun refreshToken(preferencesManager: PreferencesManager) {
        viewModelScope.launch {
            val token = preferencesManager.getUserData.first()?.get(3)
            if (token!!.isNotBlank()) {
                val result = refresh(token)
                result.data?.let {
                    preferencesManager.saveUserData(it.email, it.username, it.access_token, it.refresh_token)
                }
            } else {
                _auth.value = AuthResponse("error", null, "error")
            }
        }
    }
}