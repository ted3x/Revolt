package com.example.core_datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.Flow

class UserPreferencesImpl(
    private val dataStore: DataStore<Preferences>,
    private val security: SecurityUtil
): UserPreferences {
    private object Keys {
        val loggedIn = booleanPreferencesKey(LOGGED_IN)
        val theme = booleanPreferencesKey(THEME)
    }

    fun getData() = dataStore.data

    override suspend fun changeTheme(lightTheme: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserStatus() {
        TODO("Not yet implemented")
    }

    override suspend fun setUserStatus() {
        TODO("Not yet implemented")
    }

    override fun encrypt(text: String): String {
        TODO("Not yet implemented")
    }

    override fun decrypt(text: String): String? {
        TODO("Not yet implemented")
    }

    companion object {
        private const val THEME = "theme"
        private const val LOGGED_IN = "logged_in"
    }
}