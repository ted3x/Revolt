package chat.revolt.core_datastore

interface UserPreferences {

    suspend fun changeTheme(lightTheme: Boolean)

//    suspend fun getUserStatus()
//
//    suspend fun setUserStatus()

    suspend fun clearDataStore()
}