/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:09 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:09 AM
 */

package chat.revolt.data.local.mappers

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.local.entity.user.UserEntity
import chat.revolt.domain.models.User

class StatusEntityToStatusMapper(private val presenceMapper: PresenceEntityToPresenceMapper) :
    Mapper<UserEntity.Status, User.Status> {
    override fun map(from: UserEntity.Status): User.Status {
        return User.Status(text = from.text, presence = presenceMapper.map(from.presence))
    }
}