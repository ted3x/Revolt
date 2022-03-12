/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:03 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:03 AM
 */

package chat.revolt.socket.data.ready.mapper

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.remote.mappers.channel.ChannelMapper
import chat.revolt.data.remote.mappers.server.ServerMapper
import chat.revolt.data.remote.mappers.user.UserMapper
import chat.revolt.socket.data.ready.ReadyEventDto
import chat.revolt.socket.domain.ready.ReadyEvent

class ReadyEventMapper(
    private val userMapper: UserMapper,
    private val channelMapper: ChannelMapper,
    private val serverMapper: ServerMapper
) : Mapper<ReadyEventDto, ReadyEvent> {
    override suspend fun map(from: ReadyEventDto): ReadyEvent {
        return ReadyEvent(
            users = from.users.map { userMapper.mapToDomain(it) },
            channels = from.channels.map { channelMapper.mapToDomain(it) },
            servers = from.servers.map { serverMapper.mapToDomain(it) }
        )
    }
}