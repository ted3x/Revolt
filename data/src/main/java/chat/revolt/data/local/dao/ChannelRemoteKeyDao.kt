/*
 * Created by Tedo Manvelidze(ted3x) on 3/5/22, 9:04 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/5/22, 9:04 PM
 */

package chat.revolt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chat.revolt.data.local.entity.channel.ChannelRemoteKey

@Dao
interface ChannelRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: ChannelRemoteKey)

    @Query("SELECT * FROM channel_remote_key WHERE channel = :channel")
    suspend fun remoteKeyByPost(channel: String): ChannelRemoteKey

    @Query("DELETE FROM channel_remote_key WHERE channel = :channel")
    suspend fun deleteBySubreddit(channel: String)
}