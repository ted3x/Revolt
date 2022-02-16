/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 1:07 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 1:07 AM
 */

package chat.revolt.data.local.mappers

import chat.revolt.core.UlidTimeDecoder
import chat.revolt.core.mapper.EntityMapper
import chat.revolt.data.local.entity.message.MessageEntity
import chat.revolt.data.local.entity.user.UserEntity
import chat.revolt.domain.models.Message

class MessageDBMapper(private val userDBMapper: UserDBMapper) {
    fun mapToDomain(userEntity: UserEntity, from: MessageEntity): Message {
        return Message(
            id = from.id,
            channel = from.channel,
            author = userDBMapper.mapToDomain(userEntity),
            content = from.content,
            attachments = emptyList(),
            edited = from.edited,
            mentions = from.mentions,
            replies = from.replies,
            masquerade = null
        )
    }

    fun mapToEntity(from: Message): MessageEntity {
        return MessageEntity(
            id = from.id,
            channel = from.channel,
            author = from.author.id,
            content = from.content,
            createdAt = UlidTimeDecoder.getTimestamp(from.id),
            edited = from.edited,
            mentions = from.mentions,
            replies = from.replies,
        )
    }
}