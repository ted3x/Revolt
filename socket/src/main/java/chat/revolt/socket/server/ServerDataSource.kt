/*
 * Created by Tedo Manvelidze(ted3x) on 2/18/22, 11:59 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/18/22, 11:59 PM
 */

package chat.revolt.socket.server

import chat.revolt.data.remote.dto.channel.ChannelDto
import chat.revolt.data.remote.mappers.channel.ChannelMapper
import chat.revolt.data.remote.mappers.message.MessageMapper
import chat.revolt.data.remote.type.EventType
import chat.revolt.domain.models.Message
import chat.revolt.socket.SocketAPI
import chat.revolt.socket.data.channel.mapper.ChannelActionMapper
import chat.revolt.socket.domain.channel.ChannelEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

interface ServerDataSource {
    suspend fun onMessage(channelId: String): Flow<Message>
    suspend fun onChannelStartTyping(channelId: String): Flow<ChannelEvents.ChannelStartTyping>
    suspend fun onChannelStopTyping(channelId: String): Flow<ChannelEvents.ChannelStopTyping>
    suspend fun onChannelUpdate(): Flow<ChannelEvents.ChannelUpdate>
    suspend fun onChannelCreate(): Flow<ChannelDto>
}

class ServerDataSourceImpl(
    private val socket: SocketAPI,
    private val messageMapper: MessageMapper,
    private val channelActionMapper: ChannelActionMapper,
    private val channelMapper: ChannelMapper
) : ServerDataSource {
    override suspend fun onMessage(channelId: String): Flow<Message> {
        return socket.onMessage().filter { it.channel == channelId }
            .map { messageMapper.mapToDomain(it, null) }
    }

    override suspend fun onChannelStartTyping(channelId: String): Flow<ChannelEvents.ChannelStartTyping> {
        return socket.onChannelStartTyping()
            .filter { it.id == channelId && it.type == EventType.ChannelStartTyping }
            .map { channelActionMapper.map(it) } as Flow<ChannelEvents.ChannelStartTyping>
    }

    override suspend fun onChannelStopTyping(channelId: String): Flow<ChannelEvents.ChannelStopTyping> {
        return socket.onChannelStopTyping()
            .filter { it.id == channelId && it.type == EventType.ChannelStopTyping }
            .map { channelActionMapper.map(it) } as Flow<ChannelEvents.ChannelStopTyping>
    }

    override suspend fun onChannelCreate(): Flow<ChannelDto> {
        return socket.onChannelCreate()
            .filter { it.eventType == EventType.ChannelCreate }
            .map { channelMapper.mapToDomain(it) } as Flow<ChannelDto>
    }

    override suspend fun onChannelUpdate(): Flow<ChannelEvents.ChannelUpdate> {
        return socket.onChannelUpdate().filter { it.type == EventType.ChannelUpdate }.map {
            ChannelEvents.ChannelUpdate(channel = it.data)
        }
    }
}