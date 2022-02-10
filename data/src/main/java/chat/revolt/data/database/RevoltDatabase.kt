/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 4:17 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 4:17 PM
 */

package chat.revolt.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, exportSchema = false, entities = [])
abstract class RevoltDatabase: RoomDatabase() {

}