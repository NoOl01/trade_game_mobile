package com.example.trade_game.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object ThemePreference {
    private val Context.dataStore by preferencesDataStore(name = "settings")

    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_enabled")

    suspend fun saveTheme(context: Context, isDark: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_THEME_KEY] = isDark
        }
    }

    val Context.themeFlow: Flow<Boolean>
        get() = dataStore.data.map { prefs ->
            prefs[DARK_THEME_KEY] == true
        }
}