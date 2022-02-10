/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 4:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 4:51 PM
 */

package chat.revolt.data.database

import androidx.room.Room
import org.koin.dsl.module

val databaseModule = module {
    single { Room.databaseBuilder(get(), RevoltDatabase::class.java, "Revolt-Database").build()}
    single { get<RevoltDatabase>().userDao() }
}