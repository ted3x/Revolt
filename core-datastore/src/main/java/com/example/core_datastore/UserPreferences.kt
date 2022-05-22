package com.example.core_datastore

interface UserPreferences {

    suspend fun changeTheme(lightTheme: Boolean)

    suspend fun getUserStatus()

    suspend fun setUserStatus()

    fun encrypt(text: String): String

    fun decrypt(text: String): String?
}