/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 12:53 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 11:03 PM
 */

package chat.revolt.data.remote.mappers.user

import chat.revolt.core.mapper.NullableInputListMapper
import chat.revolt.data.remote.dto.user.UserDto
import chat.revolt.domain.models.User

class RelationshipDtoToRelationshipMapper(private val relationshipStatusMapper: RelationshipStatusMapper) :
    NullableInputListMapper<UserDto.RelationshipDto, User.Relationship> {
    override suspend fun map(from: List<UserDto.RelationshipDto>?): List<User.Relationship> {
        return from?.map {
            User.Relationship(
                userId = it.id,
                status = relationshipStatusMapper.map(it.status)
            )
        } ?: emptyList()
    }
}