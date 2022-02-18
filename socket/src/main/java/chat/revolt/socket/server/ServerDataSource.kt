/*
 * Created by Tedo Manvelidze(ted3x) on 2/18/22, 11:59 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/18/22, 11:59 PM
 */

package chat.revolt.socket.server

import chat.revolt.domain.models.Message
import chat.revolt.socket.SocketAPI
import chat.revolt.socket.server.message.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

interface ServerDataSource {
    suspend fun onMessage(channelId: String): Flow<Message>
    suspend fun onChannelStartTyping(channelId: String): Flow<ChannelStartTyping>
    suspend fun onChannelStopTyping(channelId: String): Flow<ChannelStopTyping>
}

class ServerDataSourceImpl(
    private val socket: SocketAPI,
    private val messageEventMapper: MessageEventMapper,
    private val channelStartTypingMapper: ChannelStartTypingEventMapper,
    private val channelStopTypingMapper: ChannelStopTypingEventMapper
) : ServerDataSource {
    override suspend fun onMessage(channelId: String): Flow<Message> {
        return socket.onMessage().map { messageEventMapper.map(it) }
            .filter { it.channel == channelId }
    }

    override suspend fun onChannelStartTyping(channelId: String): Flow<ChannelStartTyping> {
        return socket.onChannelStartTyping().map { channelStartTypingMapper.map(it) }
            .filter { it.channelId == channelId }
    }

    override suspend fun onChannelStopTyping(channelId: String): Flow<ChannelStopTyping> {
        return socket.onChannelStopTyping().map { channelStopTypingMapper.map(it) }
            .filter { it.channelId == channelId }
    }
}