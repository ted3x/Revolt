/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:04 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:04 AM
 */

package chat.revolt.data.local.mappers

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.local.entity.user.UserEntity
import chat.revolt.domain.models.User

class RelationshipStatusMapper: Mapper<UserEntity.RelationshipStatus, User.RelationshipStatus> {
    override fun map(from: UserEntity.RelationshipStatus): User.RelationshipStatus {
        return User.RelationshipStatus.valueOf(from.name)
    }
}