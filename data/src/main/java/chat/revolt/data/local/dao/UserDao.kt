/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 12:56 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 8:42 PM
 */

package chat.revolt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chat.revolt.data.local.entity.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM `user` WHERE `id` LIKE :userId LIMIT 1")
    suspend fun getUser(userId: String): UserEntity?

    @Query("SELECT * FROM user WHERE relationship LIKE :relationship")
    suspend fun getCurrentUser(relationship: UserEntity.RelationshipStatus = UserEntity.RelationshipStatus.User): UserEntity?

    @Query("SELECT * FROM user WHERE relationship LIKE :relationship")
    fun getCurrentUserAsFlow(relationship: UserEntity.RelationshipStatus = UserEntity.RelationshipStatus.User): Flow<UserEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<UserEntity>)

    @Query("DELETE FROM `user` WHERE id LIKE :userId")
    suspend fun deleteUser(userId: String)
}