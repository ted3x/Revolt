/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:27 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:27 AM
 */

package chat.revolt.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chat.revolt.data.local.entity.channel.ChannelEntity

@Dao
interface ChannelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChannel(channelEntity: ChannelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addChannels(channels: List<ChannelEntity>)

    @Query("SELECT * FROM channel WHERE id LIKE :channelId")
    suspend fun getChannel(channelId: String): ChannelEntity?
}