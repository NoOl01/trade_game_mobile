package com.example.trade_game.domain.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.RetrofitInstance
import com.example.trade_game.domain.models.AuthResponse
import com.example.trade_game.domain.models.EventsResponse
import com.example.trade_game.domain.models.LoginRequest
import com.example.trade_game.domain.models.RefreshRequest
import com.example.trade_game.domain.models.RegisterRequest
import com.example.trade_game.domain.models.UserInfoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _auth = MutableStateFlow<AuthResponse?>(null)
    val auth: StateFlow<AuthResponse?> = _auth.asStateFlow()

    private val _userInfo = MutableStateFlow<UserInfoResponse?>(null)
    val userInfo: StateFlow<UserInfoResponse?> = _userInfo.asStateFlow()

    private val _events = MutableStateFlow<EventsResponse?>(null)
    val events: StateFlow<EventsResponse?> = _events.asStateFlow()

    fun register(
        email: String,
        name: String,
        password: String,
        preferencesManager: PreferencesManager
    ) {
        viewModelScope.launch {
            try {
                val newReg = RegisterRequest(email, name, password)
                val response = RetrofitInstance.apiService.register(newReg)
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
                _auth.value = AuthResponse("Error", null, ex.localizedMessage)
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
                _auth.value = AuthResponse("Error", null, ex.localizedMessage)
            }
        }
    }

    private suspend fun refresh(refreshToken: String): AuthResponse {
        return try {
            val newRefreshRequest = RefreshRequest(refreshToken)
            RetrofitInstance.apiService.refresh(newRefreshRequest)
        } catch (ex: Exception) {
            AuthResponse("Error", null, ex.localizedMessage)
        }
    }

    fun refreshToken(preferencesManager: PreferencesManager) {
        viewModelScope.launch {
            val token = preferencesManager.getUserData.first()?.get(3)
            if (token!!.isNotBlank()) {
                val result = refresh(token)
                result.data?.let {
                    preferencesManager.saveUserData(
                        it.email,
                        it.username,
                        it.access_token,
                        it.refresh_token
                    )
                }
            } else {
                _auth.value = AuthResponse("Error", null, "error")
            }
        }
    }

    fun getUserInfo(preferencesManager: PreferencesManager) {
        viewModelScope.launch {
            try {
                val token = preferencesManager.getUserData.first()?.get(2)
                if (token!!.isNotBlank()) {
                    val result = RetrofitInstance.apiService.getUserInfo("Bearer $token")
                    _userInfo.value = result
                }
            } catch (ex: Exception) {
                _userInfo.value = UserInfoResponse("Error", null, ex.localizedMessage)
            }
        }
    }

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
