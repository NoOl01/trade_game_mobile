package com.example.trade_game.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "app_prefs")

class PreferencesManager(private val context: Context){
    private val userKey = stringPreferencesKey("user_data")

    val getUserData: Flow<Array<String>?> = context.dataStore.data
        .map { preferences ->
            preferences[userKey]?.split("|")?.let {
                if (it.size == 4) Array(4) { index -> it[index] } else null
            } ?: Array(4) {" "}
        }

    suspend fun saveUserData(email: String, userName: String, accessToken: String, refreshToken: String) {
        val userData = "$email|$userName|$accessToken|$refreshToken"
        context.dataStore.edit { preferences ->
            preferences[userKey] = userData
        }
    }

    suspend fun deleteUserData(){
        context.dataStore.edit { preferences ->
            preferences.remove(userKey)
        }
    }
}