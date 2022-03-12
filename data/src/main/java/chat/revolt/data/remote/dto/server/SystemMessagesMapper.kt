/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:32 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:32 AM
 */

package chat.revolt.data.remote.dto.server

import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ChannelRepository

class SystemMessagesMapper(private val channelRepository: ChannelRepository) {

    suspend fun mapToDomain(from: ServerDto.SystemMessageChannelsDto): Server.SystemMessageChannels {
        return Server.SystemMessageChannels(
            userJoined = from.userJoined?.let { channelRepository.getChannel(it) },
            userLeft = from.userLeft?.let { channelRepository.getChannel(it) },
            userKicked = from.userKicked?.let { channelRepository.getChannel(it) },
            userBanned = from.userBanned?.let { channelRepository.getChannel(it) },
        )
    }

    fun mapToDto(from: Server.SystemMessageChannels): ServerDto.SystemMessageChannelsDto {
        return ServerDto.SystemMessageChannelsDto(
            userJoined = from.userJoined?.id,
            userLeft = from.userLeft?.id,
            userKicked = from.userKicked?.id,
            userBanned = from.userBanned?.id
        )
    }
}