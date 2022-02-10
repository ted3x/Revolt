/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:19 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:19 AM
 */

package chat.revolt.data.local.mappers

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.local.entity.user.UserEntity
import chat.revolt.domain.models.User

class BotEntityToBotMapper: Mapper<UserEntity.Bot, User.Bot> {
    override fun map(from: UserEntity.Bot): User.Bot {
        return User.Bot(ownerId = from.ownerId)
    }
}