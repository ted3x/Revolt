/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 12:15 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 12:15 AM
 */

package chat.revolt.data.local.mappers.server

import chat.revolt.data.local.entity.server.ServerEntity
import chat.revolt.domain.models.server.Server

class ServerRolesEntityMapper(private val permissionsMapper: RolePermissionsEntityMapper) {
    fun mapToDomain(id: String, from: ServerEntity.Role): Server.Role {
        return Server.Role(
            id = id,
            name = from.name,
            permissions = permissionsMapper.mapToDomain(from.permissions),
            color = from.color,
            hoist = from.hoist,
            rank = from.rank
        )
    }

    fun mapToEntity(from: List<Server.Role>): Map<String, ServerEntity.Role> {
        return from.associate {
            it.id to ServerEntity.Role(
                name = it.name,
                permissions = permissionsMapper.mapToEntity(it.permissions),
                color = it.color,
                hoist = it.hoist,
                rank = it.rank
            )
        }
    }
}