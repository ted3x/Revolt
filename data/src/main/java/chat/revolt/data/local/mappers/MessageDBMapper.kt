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
import chat.revolt.domain.repository.UserRepository

class MessageDBMapper(
    private val userDBMapper: UserDBMapper,
    private val userRepository: UserRepository
) {
    suspend fun mapToDomain(userEntity: UserEntity, from: MessageEntity): Message {
        return Message(
            id = from.id,
            channel = from.channel,
            author = userDBMapper.mapToDomain(userEntity),
            content = from.content.map(),
            attachments = emptyList(),
            edited = from.edited,
            mentions = from.mentions?.let { userRepository.getUsers(it) },
            replies = from.replies,
            masquerade = null
        )
    }

    private fun MessageEntity.ContentEntity.map(): Message.Content {
        return when (this) {
            is MessageEntity.ContentEntity.ChannelDescriptionChanged -> Message.Content.ChannelDescriptionChanged(
                changedBy = this.changedBy
            )
            is MessageEntity.ContentEntity.ChannelIconChanged -> Message.Content.ChannelIconChanged(
                changedBy = this.changedBy
            )
            is MessageEntity.ContentEntity.ChannelRenamed -> Message.Content.ChannelRenamed(
                name = this.name,
                renamedBy = this.renamedBy
            )
            is MessageEntity.ContentEntity.Message -> Message.Content.Message(content = this.content)
            is MessageEntity.ContentEntity.Text -> Message.Content.Text(content = this.content)
            is MessageEntity.ContentEntity.UserAdded -> Message.Content.UserAdded(
                addedUserId = this.addedUserId,
                addedBy = this.addedBy
            )
            is MessageEntity.ContentEntity.UserBanned -> Message.Content.UserBanned(userId = this.userId)
            is MessageEntity.ContentEntity.UserJoined -> Message.Content.UserJoined(userId = this.userId)
            is MessageEntity.ContentEntity.UserKicked -> Message.Content.UserKicked(userId = this.userId)
            is MessageEntity.ContentEntity.UserLeft -> Message.Content.UserLeft(userId = this.userId)
            is MessageEntity.ContentEntity.UserRemove -> Message.Content.UserRemove(
                removedUserId = this.removedUserId,
                removedBy = this.removedBy
            )
        }
    }

    fun mapToEntity(from: Message): MessageEntity {
        return MessageEntity(
            id = from.id,
            channel = from.channel,
            author = from.author.id,
            content = from.content.map(),
            createdAt = UlidTimeDecoder.getTimestamp(from.id),
            edited = from.edited,
            mentions = from.mentions?.map { it.id },
            replies = from.replies,
        )
    }

    private fun Message.Content.map(): MessageEntity.ContentEntity {
        return when (this) {
            is Message.Content.ChannelDescriptionChanged -> MessageEntity.ContentEntity.ChannelDescriptionChanged(
                changedBy = this.changedBy
            )
            is Message.Content.ChannelIconChanged -> MessageEntity.ContentEntity.ChannelIconChanged(
                changedBy = this.changedBy
            )
            is Message.Content.ChannelRenamed -> MessageEntity.ContentEntity.ChannelRenamed(
                name = this.name,
                renamedBy = this.renamedBy
            )
            is Message.Content.Message -> MessageEntity.ContentEntity.Message(content = this.content)
            is Message.Content.Text -> MessageEntity.ContentEntity.Text(content = this.content)
            is Message.Content.UserAdded -> MessageEntity.ContentEntity.UserAdded(
                addedUserId = this.addedUserId,
                addedBy = this.addedBy
            )
            is Message.Content.UserBanned -> MessageEntity.ContentEntity.UserBanned(userId = this.userId)
            is Message.Content.UserJoined -> MessageEntity.ContentEntity.UserJoined(userId = this.userId)
            is Message.Content.UserKicked -> MessageEntity.ContentEntity.UserKicked(userId = this.userId)
            is Message.Content.UserLeft -> MessageEntity.ContentEntity.UserLeft(userId = this.userId)
            is Message.Content.UserRemove -> MessageEntity.ContentEntity.UserRemove(
                removedUserId = this.removedUserId,
                removedBy = this.removedBy
            )
        }
    }

}