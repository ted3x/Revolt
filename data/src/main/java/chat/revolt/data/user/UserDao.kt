/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 4:18 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 4:18 PM
 */

package chat.revolt.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//@Dao
//interface UserDao {
//
//    @Query("SELECT * FROM `user` WHERE `id` LIKE :userId LIMIT 1")
//    suspend fun getUser(userId: String): UserEntity?
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addUser(user: UserEntity)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addUsers(users: List<UserEntity>)
//
//    @Query("DELETE FROM `user` WHERE id LIKE :userId")
//    suspend fun deleteUser(userId: String)
//}