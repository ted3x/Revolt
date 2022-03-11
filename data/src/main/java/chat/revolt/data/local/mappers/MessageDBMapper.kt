/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 1:07 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 1:07 AM
 */

package chat.revolt.data.local.mappers

import chat.revolt.domain.UlidTimeDecoder
import chat.revolt.data.local.entity.message.MessageEntity
import chat.revolt.domain.models.Message
import chat.revolt.domain.repository.UserRepository

class MessageDBMapper(
    private val userDBMapper: UserDBMapper,
    private val userRepository: UserRepository,
): EntityDomainMapper<MessageEntity, Message> {
    override suspend fun mapToDomain(from: MessageEntity): Message {
        return Message(
            id = from.id,
            channel = from.channel,
            author = userRepository.getUser(from.author),
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
                changedBy = userDBMapper.mapToDomain(this.changedBy)
            )
            is MessageEntity.ContentEntity.ChannelIconChanged -> Message.Content.ChannelIconChanged(
                changedBy = userDBMapper.mapToDomain(this.changedBy)
            )
            is MessageEntity.ContentEntity.ChannelRenamed -> Message.Content.ChannelRenamed(
                name = this.name,
                renamedBy = userDBMapper.mapToDomain(this.renamedBy)
            )
            is MessageEntity.ContentEntity.Message -> Message.Content.Message(content = this.content)
            is MessageEntity.ContentEntity.Text -> Message.Content.Text(content = this.content)
            is MessageEntity.ContentEntity.UserAdded -> Message.Content.UserAdded(
                addedUser = userDBMapper.mapToDomain(this.addedUser),
                addedBy = userDBMapper.mapToDomain(this.addedBy)
            )
            is MessageEntity.ContentEntity.UserBanned -> Message.Content.UserBanned(
                user = userDBMapper.mapToDomain(
                    this.user
                )
            )
            is MessageEntity.ContentEntity.UserJoined -> Message.Content.UserJoined(
                user = userDBMapper.mapToDomain(
                    this.user
                )
            )
            is MessageEntity.ContentEntity.UserKicked -> Message.Content.UserKicked(
                user = userDBMapper.mapToDomain(
                    this.user
                )
            )
            is MessageEntity.ContentEntity.UserLeft -> Message.Content.UserLeft(
                user = userDBMapper.mapToDomain(
                    this.user
                )
            )
            is MessageEntity.ContentEntity.UserRemove -> Message.Content.UserRemove(
                removedUser = userDBMapper.mapToDomain(this.removedUser),
                removedBy = userDBMapper.mapToDomain(this.removedBy)
            )
        }
    }

    override suspend fun mapToEntity(from: Message): MessageEntity {
        return MessageEntity(
            id = from.id,
            channel = from.channel,
            author = from.author.id,
            content = from.content.map(),
            createdAt = UlidTimeDecoder.getTimestamp(from.id),
            edited = from.edited,
            mentions = from.mentions?.map { it.id },
            replies = from.replies,
            synchronizedAt = System.currentTimeMillis()
        )
    }

    private fun Message.Content.map(): MessageEntity.ContentEntity {
        return when (this) {
            is Message.Content.ChannelDescriptionChanged -> MessageEntity.ContentEntity.ChannelDescriptionChanged(
                changedBy = userDBMapper.mapToEntity(this.changedBy)
            )
            is Message.Content.ChannelIconChanged -> MessageEntity.ContentEntity.ChannelIconChanged(
                changedBy = userDBMapper.mapToEntity(this.changedBy)
            )
            is Message.Content.ChannelRenamed -> MessageEntity.ContentEntity.ChannelRenamed(
                name = this.name,
                renamedBy = userDBMapper.mapToEntity(this.renamedBy)
            )
            is Message.Content.Message -> MessageEntity.ContentEntity.Message(content = this.content)
            is Message.Content.Text -> MessageEntity.ContentEntity.Text(content = this.content)
            is Message.Content.UserAdded -> MessageEntity.ContentEntity.UserAdded(
                addedUser = userDBMapper.mapToEntity(this.addedUser),
                addedBy = userDBMapper.mapToEntity(this.addedBy)
            )
            is Message.Content.UserBanned -> MessageEntity.ContentEntity.UserBanned(
                user = userDBMapper.mapToEntity(
                    this.user
                )
            )
            is Message.Content.UserJoined -> MessageEntity.ContentEntity.UserJoined(
                user = userDBMapper.mapToEntity(
                    this.user
                )
            )
            is Message.Content.UserKicked -> MessageEntity.ContentEntity.UserKicked(
                user = userDBMapper.mapToEntity(
                    this.user
                )
            )
            is Message.Content.UserLeft -> MessageEntity.ContentEntity.UserLeft(
                user = userDBMapper.mapToEntity(
                    this.user
                )
            )
            is Message.Content.UserRemove -> MessageEntity.ContentEntity.UserRemove(
                removedUser = userDBMapper.mapToEntity(this.removedUser),
                removedBy = userDBMapper.mapToEntity(this.removedBy)
            )
        }
    }

}