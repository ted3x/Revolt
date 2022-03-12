/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 12:53 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 11:02 PM
 */

package chat.revolt.data.remote.mappers.user

import chat.revolt.domain.models.User

class RelationshipStatusMapper {
    fun mapToDomain(from: String): User.RelationshipStatus {
        return User.RelationshipStatus.valueOf(from)
    }

    fun mapToDto(from: User.RelationshipStatus): String {
        return from.name
    }
}