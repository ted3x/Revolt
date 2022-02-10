/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:26 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 8:42 PM
 */

package chat.revolt.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import chat.revolt.data.local.dao.UserDao

@Database(version = 1, exportSchema = false, entities = [])
abstract class RevoltDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}