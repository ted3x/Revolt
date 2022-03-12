/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:32 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:32 AM
 */

package chat.revolt.data.remote.dto.server

import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ChannelRepository

class ServerCategoryMapper(private val channelRepository: ChannelRepository) {

    suspend fun mapToDomain(from: ServerDto.CategoryDto): Server.Category {
        return Server.Category(
            id = from.id,
            title = from.title,
            channels = from.channels.map { channelRepository.getChannel(it) }
        )
    }

    fun mapToDto(from: Server.Category): ServerDto.CategoryDto {
        return ServerDto.CategoryDto(
            id = from.id,
            title = from.title,
            channels = from.channels.map { it.id }
        )
    }
}