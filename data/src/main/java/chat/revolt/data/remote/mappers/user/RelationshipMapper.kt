/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 12:53 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 11:03 PM
 */

package chat.revolt.data.remote.mappers.user

import chat.revolt.data.remote.dto.user.UserDto
import chat.revolt.domain.models.User

class RelationshipMapper(private val relationshipStatusMapper: RelationshipStatusMapper) {

    fun mapToDomain(from: List<UserDto.RelationshipDto>?): List<User.Relationship> {
        return from?.map {
            User.Relationship(
                userId = it.id,
                status = relationshipStatusMapper.mapToDomain(it.status)
            )
        } ?: emptyList()
    }

    fun mapToDto(from: List<User.Relationship>?): List<UserDto.RelationshipDto> {
        return from?.map {
            UserDto.RelationshipDto(
                id = it.userId,
                status = relationshipStatusMapper.mapToDto(it.status)
            )
        } ?: emptyList()
    }
}