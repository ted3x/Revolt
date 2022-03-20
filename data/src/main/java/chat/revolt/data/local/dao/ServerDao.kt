/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:58 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:58 PM
 */

package chat.revolt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chat.revolt.data.local.entity.server.ServerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addServers(servers: List<ServerEntity>)

    @Query("SELECT * FROM server where id LIKE :serverId LIMIT 1")
    suspend fun getServer(serverId: String): ServerEntity?

    @Query("SELECT * FROM server where id LIKE :serverId LIMIT 1")
    fun getServerAsFlow(serverId: String): Flow<ServerEntity>

    @Query("SELECT * FROM server")
    fun getServers(): Flow<List<ServerEntity>>

    @Query("UPDATE server SET categories = :categories WHERE id LIKE :serverId")
    suspend fun updateCategory(serverId: String, categories: List<ServerEntity.Category>)

    @Query("UPDATE server SET selectedChannelId = :chanenlId WHERE id LIKE :serverId")
    suspend fun updateSelectedChannel(serverId: String, chanenlId: String)
}