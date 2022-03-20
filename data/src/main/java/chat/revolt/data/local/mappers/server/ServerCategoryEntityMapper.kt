/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 12:04 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 12:04 AM
 */

package chat.revolt.data.local.mappers.server

import chat.revolt.data.local.entity.server.ServerEntity
import chat.revolt.data.local.mappers.EntityDomainMapper
import chat.revolt.domain.models.server.Server

class ServerCategoryEntityMapper: EntityDomainMapper<ServerEntity.Category, Server.Category> {
    override fun mapToDomain(from: ServerEntity.Category): Server.Category {
        return Server.Category(
            id = from.id,
            title = from.title,
            channels = from.channels,
            isVisible = from.isVisible
        )
    }

    override fun mapToEntity(from: Server.Category): ServerEntity.Category {
        return ServerEntity.Category(
            id = from.id,
            title = from.title,
            channels = from.channels,
            isVisible = from.isVisible ?: true
        )
    }
}