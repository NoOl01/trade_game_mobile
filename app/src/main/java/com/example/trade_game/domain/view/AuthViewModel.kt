package com.example.trade_game.domain.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.RetrofitInstance
import com.example.trade_game.domain.models.AuthResponse
import com.example.trade_game.domain.models.ChangePasswordRequest
import com.example.trade_game.domain.models.LoginRequest
import com.example.trade_game.domain.models.RecoverCheckRequest
import com.example.trade_game.domain.models.RecoverSendRequest
import com.example.trade_game.domain.models.RefreshRequest
import com.example.trade_game.domain.models.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _auth = MutableStateFlow<AuthResponse?>(null)
    val auth: StateFlow<AuthResponse?> = _auth.asStateFlow()

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
                        response.data.user_id,
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
                        response.data.user_id,
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
            val token = preferencesManager.getUserData.first()?.get(4)
            if (token!!.isNotBlank()) {
                val result = refresh(token)
                result.data?.let {
                    preferencesManager.saveUserData(
                        it.user_id,
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

    fun recoverSend(email: String, navigation: NavController) {
        try {
            viewModelScope.launch {
                val recover = RecoverSendRequest(email)
                val response = RetrofitInstance.apiService.recoverSend(recover)
                if (response.status == "Ok") {
                    navigation.navigate("OtpVerificationScreen/$email")
                }
            }
        } catch (_: Exception) {
            return
        }
    }

    fun recoverCheck(
        email: String,
        code: Int,
        navigation: NavController
    ) {
        try {
            viewModelScope.launch {
                val recover = RecoverCheckRequest(email, code)
                val response = RetrofitInstance.apiService.recoverCheck(recover)
                if (response.status == "Ok") {
                    val accessToken = response.data!!.access_token
                    navigation.navigate("ChangePasswordScreen/$accessToken")
                }
            }
        } catch (_: Exception) {
            return
        }
    }

    fun changePassword(password: String, jwt: String, navigation: NavController) {
        try {
            viewModelScope.launch {
                val change = ChangePasswordRequest(password)
                val response = RetrofitInstance.apiService.changePassword(change, jwt)
                if (response.status == "Ok") {
                    navigation.navigate("LoginScreen")
                }
            }
        } catch (_: Exception) {
            return
        }
    }
}