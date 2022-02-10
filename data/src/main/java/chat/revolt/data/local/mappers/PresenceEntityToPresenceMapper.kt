/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:13 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:13 AM
 */

package chat.revolt.data.local.mappers

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.local.entity.user.UserEntity
import chat.revolt.domain.models.User

class PresenceEntityToPresenceMapper: Mapper<UserEntity.Presence, User.Presence> {
    override fun map(from: UserEntity.Presence): User.Presence {
        return User.Presence.valueOf(from.name)
    }
}