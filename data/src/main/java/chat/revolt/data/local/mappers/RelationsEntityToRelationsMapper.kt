/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:08 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:03 AM
 */

package chat.revolt.data.local.mappers

import chat.revolt.core.mapper.ListMapper
import chat.revolt.core.mapper.NullableInputListMapper
import chat.revolt.data.local.entity.user.UserEntity
import chat.revolt.domain.models.User

class RelationsEntityToRelationsMapper(private val statusMapper: RelationshipStatusMapper) :
    NullableInputListMapper<UserEntity.Relationship, User.Relationship> {

    override fun map(from: List<UserEntity.Relationship>?): List<User.Relationship> {
        return from?.map { User.Relationship(userId = it.userId, status = statusMapper.map(it.status)) } ?: emptyList()
    }
}