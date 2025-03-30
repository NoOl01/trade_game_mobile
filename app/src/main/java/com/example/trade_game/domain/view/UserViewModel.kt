package com.example.trade_game.domain.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trade_game.data.PreferencesManager
import com.example.trade_game.domain.RetrofitInstance
import com.example.trade_game.domain.models.UserInfoResponse
import com.example.trade_game.domain.models.UserPlaceResponse
import com.example.trade_game.domain.models.UsersTopResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _userInfo = MutableStateFlow<UserInfoResponse?>(null)
    val userInfo: StateFlow<UserInfoResponse?> = _userInfo.asStateFlow()

    private val _userPlace = MutableStateFlow<UserPlaceResponse?>(null)
    val userPlace: StateFlow<UserPlaceResponse?> = _userPlace.asStateFlow()

    private val _usersTop = MutableStateFlow<UsersTopResponse?>(null)
    val usersTop: StateFlow<UsersTopResponse?> = _usersTop.asStateFlow()

    fun getUserInfo(userId: Int) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.apiService.getUserInfo(userId)
                _userInfo.value = result

            } catch (ex: Exception) {
                _userInfo.value = UserInfoResponse("Error", null, ex.localizedMessage)
            }
        }
    }

    fun getUserPlace(userId: Int) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.apiService.getUserPlace(userId)
                _userPlace.value = result
            } catch (ex: Exception) {
                _userPlace.value = UserPlaceResponse("Error", null, ex.localizedMessage)
            }
        }
    }

    fun getUsersTop(count: Int) {
        viewModelScope.launch {
            try {
                val result = RetrofitInstance.apiService.getUsersTop(count)
                _usersTop.value = result
            } catch (ex: Exception) {
                _usersTop.value = UsersTopResponse("Error", emptyList(), ex.localizedMessage)
            }
        }
    }
}
