/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 12:54 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 12:50 AM
 */

package chat.revolt.data.remote.mappers.user

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.remote.dto.user.UserDto
import chat.revolt.domain.models.User

class BotDtoToBotMapper: Mapper<UserDto.BotDto?, User.Bot?> {
    override suspend fun map(from: UserDto.BotDto?): User.Bot? {
        return from?.owner?.let { User.Bot(ownerId = it) }
    }
}