/*
 * Created by Tedo Manvelidze(ted3x) on 6/14/22, 11:05 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 6/14/22, 11:05 PM
 */

package chat.revolt.core_datastore.di

import chat.revolt.core_datastore.SecurityUtil
import chat.revolt.core_datastore.UserPreferences
import chat.revolt.core_datastore.UserPreferencesImpl
import org.koin.dsl.module

val dataStoreModule = module {
    // missing inject datastore
    single { SecurityUtil() }
    single<UserPreferences> { UserPreferencesImpl(dataStore = get(), security = get()) }
}