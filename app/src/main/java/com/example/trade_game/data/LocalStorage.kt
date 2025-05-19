package com.example.trade_game.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore by preferencesDataStore(name = "app_prefs")

data class UserData(
    val id: Int,
    val email: String,
    val userName: String,
    val accessToken: String,
    val refreshToken: String
)

class PreferencesManager(private val context: Context){
    private val userKey = stringPreferencesKey("user_data")

    suspend fun getUserData(): UserData? {
        val preferences = context.dataStore.data.first()
        return preferences[userKey]?.split("|")?.takeIf { it.size == 5 }?.let {
            val id = it[0].toIntOrNull() ?: return null
            UserData(
                id = id,
                email = it[1],
                userName = it[2],
                accessToken = it[3],
                refreshToken = it[4]
            )
        }
    }

    suspend fun saveUserData(id: Int, email: String, userName: String, accessToken: String, refreshToken: String) {
        val userData = "$id|$email|$userName|$accessToken|$refreshToken"
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