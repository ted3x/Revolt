/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 12:06 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 12:06 AM
 */

package chat.revolt.data.local.mappers.server

import chat.revolt.data.local.entity.server.ServerEntity
import chat.revolt.data.local.mappers.EntityDomainMapper
import chat.revolt.domain.models.server.Server

class SystemMessagesEntityMapper: EntityDomainMapper<ServerEntity.SystemMessageChannels, Server.SystemMessageChannels> {
    override fun mapToDomain(from: ServerEntity.SystemMessageChannels): Server.SystemMessageChannels {
        return Server.SystemMessageChannels(
            userJoined = from.userJoined,
            userLeft = from.userLeft,
            userBanned = from.userBanned,
            userKicked = from.userKicked
        )
    }

    override fun mapToEntity(from: Server.SystemMessageChannels): ServerEntity.SystemMessageChannels {
        return ServerEntity.SystemMessageChannels(
            userJoined = from.userJoined,
            userLeft = from.userLeft,
            userBanned = from.userBanned,
            userKicked = from.userKicked
        )
    }
}