/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 12:54 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 11:02 PM
 */

package chat.revolt.data.remote.mappers.user

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.remote.dto.user.UserDto
import chat.revolt.domain.models.User

class StatusDtoToStatusMapper : Mapper<UserDto.StatusDto?, User.Status> {
    override suspend fun map(from: UserDto.StatusDto?): User.Status {
        return if(from != null) User.Status(
            text = from.text ?: "",
            presence = if (from.presence != null) User.Presence.valueOf(from.presence) else User.Presence.Idle
        ) else User.Status.NOT_LOADED
    }
}