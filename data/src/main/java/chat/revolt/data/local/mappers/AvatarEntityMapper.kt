/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 4:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 4:55 PM
 */

package chat.revolt.data.local.mappers

import chat.revolt.data.local.entity.user.UserEntity
import chat.revolt.domain.models.User

class AvatarEntityMapper(private val metadataMapper: MetadataEntityMapper) {

    fun mapToDomain(from: UserEntity.Avatar): User.Avatar {
        return User.Avatar(
            id = from.id,
            tag = from.tag,
            size = from.size,
            filename = from.filename,
            metadata = metadataMapper.mapToDomain(from.metadata),
            contentType = from.contentType
        )
    }

    fun mapToEntity(from: User.Avatar): UserEntity.Avatar {
        return UserEntity.Avatar(
            id = from.id,
            tag = from.tag,
            size = from.size,
            filename = from.filename,
            metadata = metadataMapper.mapToEntity(from.metadata),
            contentType = from.contentType
        )
    }
}