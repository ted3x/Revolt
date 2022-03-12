/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:05 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:05 AM
 */

package chat.revolt.data.remote.mappers.server

import chat.revolt.data.remote.dto.server.ServerDto
import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.UserRepository

class ServerMapper(private val userRepository: UserRepository) {

    suspend fun mapToDomain(from: ServerDto): Server {
        return Server(
            id = from.id,
            owner = userRepository.getUser(userId = from.owner),
            name = from.name,
            description = from.description,
            channels = from.channels.
        )
    }
}