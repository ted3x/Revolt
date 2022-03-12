/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:32 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:32 AM
 */

package chat.revolt.data.remote.dto.server

import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ChannelRepository

class ServerRolesMapper {

    fun mapToDomain(id: String, from: ServerDto.RoleDto): Server.Role {
        return Server.Role(
            id = id,
            name = from.name,
            permissions = from.permissions,
            color = from.color,
            hoist = from.hoist,
            rank = from.rank
        )
    }

    fun mapToDto(from: List<Server.Role>): Map<String, ServerDto.RoleDto> {
        return from.associate {
            it.id to ServerDto.RoleDto(
                name = it.name,
                permissions = it.permissions,
                color = it.color,
                hoist = it.hoist,
                rank = it.rank
            )
        }
    }
}