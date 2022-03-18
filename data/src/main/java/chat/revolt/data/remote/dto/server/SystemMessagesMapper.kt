/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:32 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:32 AM
 */

package chat.revolt.data.remote.dto.server

import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ChannelRepository

class SystemMessagesMapper {

    fun mapToDomain(from: ServerDto.SystemMessageChannelsDto): Server.SystemMessageChannels {
        return Server.SystemMessageChannels(
            userJoined = from.userJoined,
            userLeft = from.userLeft,
            userKicked = from.userKicked,
            userBanned = from.userBanned,
        )
    }

    fun mapToDto(from: Server.SystemMessageChannels): ServerDto.SystemMessageChannelsDto {
        return ServerDto.SystemMessageChannelsDto(
            userJoined = from.userJoined,
            userLeft = from.userLeft,
            userKicked = from.userKicked,
            userBanned = from.userBanned
        )
    }
}