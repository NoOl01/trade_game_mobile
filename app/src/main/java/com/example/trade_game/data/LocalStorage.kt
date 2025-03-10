package com.example.trade_game.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "app_prefs")

class PreferencesManager(private val context: Context){
    private val accessTokenKey = stringPreferencesKey("access_token")
    private val refreshTokenKey = stringPreferencesKey("refresh_token")
    private val userKey = stringPreferencesKey("user_data")

    val getAccessToken: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[accessTokenKey] }

    val getRefreshToken: Flow<String?> = context.dataStore.data
        .map { preferences -> preferences[refreshTokenKey] }

    val getUserData: Flow<Pair<String, String>?> = context.dataStore.data
        .map { preferences ->
            preferences[userKey]?.split("|")?.let{
                if (it.size == 2) Pair(it[0], it[1]) else null
            }
        }

    suspend fun setAccessToken(value: String){
        context.dataStore.edit { preferences ->
            preferences[accessTokenKey] = value
        }
    }

    suspend fun setRefreshToken(value: String){
        context.dataStore.edit { preferences ->
            preferences[refreshTokenKey] = value
        }
    }

    suspend fun saveUserData(email: String, userName: String){
        val userData = "$email|$userName"
        context.dataStore.edit { preferences ->
            preferences[userKey] = userData
        }
    }

    suspend fun deleteAccessToken(){
        context.dataStore.edit { preferences ->
            preferences.remove(accessTokenKey)
        }
    }

    suspend fun deleteRefreshToken(){
        context.dataStore.edit { preferences ->
            preferences.remove(refreshTokenKey)
        }
    }

    suspend fun deleteUserData(){
        context.dataStore.edit { preferences ->
            preferences.remove(userKey)
        }
    }
}