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
    private val attachmentEntityMapper: AttachmentEntityMapper
): EntityDomainMapper<MessageEntity, Message> {
    override fun mapToDomain(from: MessageEntity): Message {
        return Message(
            id = from.id,
            channel = from.channel,
            authorId = from.author,
            content = from.content.map(),
            attachments = from.attachments?.let { attachmentEntityMapper.mapToDomain(it) },
            edited = from.edited,
            mentions = from.mentions,
            replies = from.replies,
            masquerade = null
        )
    }

    private fun MessageEntity.ContentEntity.map(): Message.Content {
        return when (this) {
            is MessageEntity.ContentEntity.ChannelDescriptionChanged -> Message.Content.ChannelDescriptionChanged(
                changedById = this.changedById,
                changedByUsername = this.changedByUsername,
                changedByAvatarUrl = this.changedByAvatarUrl
            )
            is MessageEntity.ContentEntity.ChannelIconChanged -> Message.Content.ChannelIconChanged(
                changedById = this.changedById,
                changedByUsername = this.changedByUsername,
                changedByAvatarUrl = this.changedByAvatarUrl,
            )
            is MessageEntity.ContentEntity.ChannelRenamed -> Message.Content.ChannelRenamed(
                name = this.name,
                renamedById = this.renamedById,
                renamedByUsername = this.renamedByUsername,
                renamedByAvatarUrl = this.renamedByAvatarUrl,
            )
            is MessageEntity.ContentEntity.Message -> Message.Content.Message(content = this.content)
            is MessageEntity.ContentEntity.Text -> Message.Content.Text(content = this.content)
            is MessageEntity.ContentEntity.UserAdded -> Message.Content.UserAdded(
                addedUserId = this.addedUserId,
                addedUsername = this.addedUsername,
                addedBy = this.addedById,
                addedByUsername = this.addedByUsername
            )
            is MessageEntity.ContentEntity.UserBanned -> Message.Content.UserBanned(
                userId = this.userId,
                username = this.username,
                userAvatarUrl = this.userAvatarUrl,
            )
            is MessageEntity.ContentEntity.UserJoined -> Message.Content.UserJoined(
                userId = this.userId,
                username = this.username,
                userAvatarUrl = this.userAvatarUrl,
            )
            is MessageEntity.ContentEntity.UserKicked -> Message.Content.UserKicked(
                userId = this.userId,
                username = this.username,
                userAvatarUrl = this.userAvatarUrl,
            )
            is MessageEntity.ContentEntity.UserLeft -> Message.Content.UserLeft(
                userId = this.userId,
                username = this.username,
                userAvatarUrl = this.userAvatarUrl,
            )
            is MessageEntity.ContentEntity.UserRemove -> Message.Content.UserRemove(
                removedUserId = this.removedUserId,
                removedUsername = this.removedUsername,
                removedById = this.removedById,
                removedByUsername = this.removedByUsername,
            )
        }
    }

    override fun mapToEntity(from: Message): MessageEntity {
        return MessageEntity(
            id = from.id,
            channel = from.channel,
            author = from.authorId,
            content = from.content.map(),
            createdAt = UlidTimeDecoder.getTimestamp(from.id),
            attachments = from.attachments?.map { attachmentEntityMapper.mapToEntity(it) },
            edited = from.edited,
            mentions = from.mentions,
            replies = from.replies,
            synchronizedAt = System.currentTimeMillis()
        )
    }

    private fun Message.Content.map(): MessageEntity.ContentEntity {
        return when (this) {
            is Message.Content.ChannelDescriptionChanged -> MessageEntity.ContentEntity.ChannelDescriptionChanged(
                changedById = this.changedById,
                changedByUsername = this.changedByUsername,
                changedByAvatarUrl = this.changedByAvatarUrl,
            )
            is Message.Content.ChannelIconChanged -> MessageEntity.ContentEntity.ChannelIconChanged(
                changedById = this.changedById,
                changedByUsername = this.changedByUsername,
                changedByAvatarUrl = this.changedByAvatarUrl,
            )
            is Message.Content.ChannelRenamed -> MessageEntity.ContentEntity.ChannelRenamed(
                name = this.name,
                renamedById = this.renamedById,
                renamedByUsername = this.renamedByUsername,
                renamedByAvatarUrl = this.renamedByAvatarUrl,
            )
            is Message.Content.Message -> MessageEntity.ContentEntity.Message(content = this.content)
            is Message.Content.Text -> MessageEntity.ContentEntity.Text(content = this.content)
            is Message.Content.UserAdded -> MessageEntity.ContentEntity.UserAdded(
                addedUserId = this.addedUserId,
                addedUsername = this.addedUsername,
                addedById = this.addedBy,
                addedByUsername = this.addedByUsername,
            )
            is Message.Content.UserBanned -> MessageEntity.ContentEntity.UserBanned(
                userId = this.userId,
                username = this.username,
                userAvatarUrl = this.userAvatarUrl,
            )
            is Message.Content.UserJoined -> MessageEntity.ContentEntity.UserJoined(
                userId = this.userId,
                username = this.username,
                userAvatarUrl = this.userAvatarUrl,
            )
            is Message.Content.UserKicked -> MessageEntity.ContentEntity.UserKicked(
                userId = this.userId,
                username = this.username,
                userAvatarUrl = this.userAvatarUrl,
            )
            is Message.Content.UserLeft -> MessageEntity.ContentEntity.UserLeft(
                userId = this.userId,
                username = this.username,
                userAvatarUrl = this.userAvatarUrl,
            )
            is Message.Content.UserRemove -> MessageEntity.ContentEntity.UserRemove(
                removedUserId = this.removedUserId,
                removedUsername = this.removedUsername,
                removedById = this.removedById,
                removedByUsername = this.removedByUsername,
            )
        }
    }

}