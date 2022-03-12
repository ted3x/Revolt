/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 4:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 4:55 PM
 */

package chat.revolt.data.remote.mappers.user

import chat.revolt.data.remote.dto.user.UserDto
import chat.revolt.data.remote.mappers.MetadataMapper
import chat.revolt.domain.models.User

class AvatarMapper(private val metadataMapper: MetadataMapper) {

    fun mapToDomain(from: UserDto.AvatarDto): User.Avatar {
        return User.Avatar(
            id = from.id,
            tag = from.tag,
            size = from.size,
            filename = from.filename,
            metadata = metadataMapper.mapToDomain(from.metadata),
            contentType = from.contentType
        )
    }

    fun mapToDto(from: User.Avatar): UserDto.AvatarDto {
        return UserDto.AvatarDto(
            id = from.id,
            tag = from.tag,
            size = from.size,
            filename = from.filename,
            metadata = metadataMapper.mapToDto(from.metadata),
            contentType = from.contentType
        )
    }
}