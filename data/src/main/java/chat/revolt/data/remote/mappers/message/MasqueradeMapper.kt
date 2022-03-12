/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 5:18 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 5:18 PM
 */

package chat.revolt.data.remote.mappers.message

import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.domain.models.Message

class MasqueradeMapper {

    fun mapToDomain(from: MessageDto.MasqueradeDto): Message.Masquerade {
        return Message.Masquerade(name = from.name, avatar = from.avatar)
    }

    fun mapToDto(from: Message.Masquerade): MessageDto.MasqueradeDto {
        return MessageDto.MasqueradeDto(name = from.name, avatar = from.avatar)
    }
}