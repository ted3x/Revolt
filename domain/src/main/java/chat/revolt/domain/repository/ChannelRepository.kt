/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:46 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:46 AM
 */

package chat.revolt.domain.repository

import chat.revolt.domain.models.channel.Channel
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    suspend fun getChannel(channelId: String): Channel
    fun getChannels(serverId: String): Flow<List<Channel>>
    suspend fun fetchChannel(channelId: String): Channel?
    suspend fun addChannels(channels: List<Channel>)
    suspend fun syncChannels(channels: List<String>)
}