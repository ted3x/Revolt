package chat.revolt.core_datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import chat.revolt.core_datastore.UserPreferencesImpl.DataStoreConst.DATA
import chat.revolt.core_datastore.UserPreferencesImpl.DataStoreConst.SECURED_DATA
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UserPreferencesImpl(
    private val dataStore: DataStore<Preferences>,
    private val security: SecurityUtil
): UserPreferences {
    private val securityKeyAlias = "data-store"
    private val bytesToStringSeparator = "|"

    private object Keys {
        val loggedIn = booleanPreferencesKey(LOGGED_IN)
        val theme = booleanPreferencesKey(THEME)
    }

    object DataStoreConst {
        val DATA = stringPreferencesKey("data")
        val SECURED_DATA = stringPreferencesKey("secured_data")
    }

    fun getData() = dataStore.data
        .map { preferences ->
            preferences[DATA].orEmpty()
        }

    suspend fun setData(value: String) {
        dataStore.edit {
            it[DATA] = value
        }
    }

    fun getSecuredData() = dataStore.data
        .secureMap<String> { preferences ->
            preferences[SECURED_DATA].orEmpty()
        }

    suspend fun setSecuredData(value: String) {
        dataStore.secureEdit(value) { prefs, encryptedValue ->
            prefs[SECURED_DATA] = encryptedValue
        }
    }

    override suspend fun changeTheme(lightTheme: Boolean) {
        dataStore.edit { it[Keys.theme] = lightTheme }
        Log.d("UserPreferencesRepo","changeTheme $lightTheme")
    }

    override suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    private inline fun <reified T> Flow<Preferences>.secureMap(crossinline fetchValue: (value: Preferences) -> String): Flow<T> {
        return map {
            val decryptedValue = security.decryptData(
                securityKeyAlias,
                fetchValue(it).split(bytesToStringSeparator).map { it.toByte() }.toByteArray()
            )
            Json { encodeDefaults = true }.decodeFromString(decryptedValue)
        }
    }

    private suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
        value: T,
        crossinline editStore: (MutablePreferences, String) -> Unit
    ) {
        edit {
            val encryptedValue = security.encryptData(securityKeyAlias, Json.encodeToString(value))
            editStore.invoke(it, encryptedValue.joinToString(bytesToStringSeparator))
        }
    }

    companion object {
        private const val THEME = "theme"
        private const val LOGGED_IN = "logged_in"
    }
}