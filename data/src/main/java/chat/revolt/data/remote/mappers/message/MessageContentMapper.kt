/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 4:32 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 4:32 PM
 */

package chat.revolt.data.remote.mappers.message

import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User
import chat.revolt.domain.models.getUser
import chat.revolt.domain.repository.UserRepository

class MessageContentMapper(private val userRepository: UserRepository) {

    fun mapToDto(from: Message.Content): MessageDto.ContentDto {
        return when (from) {
            is Message.Content.ChannelDescriptionChanged -> MessageDto.ContentDto.ChannelDescriptionChanged(
                changedBy = from.changedById
            )
            is Message.Content.ChannelIconChanged -> MessageDto.ContentDto.ChannelIconChanged(
                changedBy = from.changedById
            )
            is Message.Content.ChannelRenamed -> MessageDto.ContentDto.ChannelRenamed(
                name = from.name,
                renamedBy = from.renamedById
            )
            is Message.Content.Message -> MessageDto.ContentDto.Message(content = from.content)
            is Message.Content.Text -> MessageDto.ContentDto.Text(content = from.content)
            is Message.Content.UserAdded -> MessageDto.ContentDto.UserAdded(
                addedUserId = from.addedUserId,
                addedBy = from.addedBy
            )
            is Message.Content.UserBanned -> MessageDto.ContentDto.UserBanned(userId = from.userId)
            is Message.Content.UserJoined -> MessageDto.ContentDto.UserJoined(userId = from.userId)
            is Message.Content.UserKicked -> MessageDto.ContentDto.UserKicked(userId = from.userId)
            is Message.Content.UserLeft -> MessageDto.ContentDto.UserLeft(userId = from.userId)
            is Message.Content.UserRemove -> MessageDto.ContentDto.UserRemove(
                removedBy = from.removedById,
                removedUserId = from.removedUserId
            )
        }

    }

    suspend fun mapToDomain(from: MessageDto.ContentDto, users: List<User>?): Message.Content {
        return when (from) {
            is MessageDto.ContentDto.ChannelDescriptionChanged -> {
                val changedBy = users.getUser(from.changedBy, userRepository.getCurrentUser())
                Message.Content.ChannelDescriptionChanged(
                    changedById = changedBy.id,
                    changedByUsername = changedBy.username,
                    changedByAvatarUrl = changedBy.avatarUrl,
                )
            }
            is MessageDto.ContentDto.ChannelIconChanged -> {
                val changedBy = users.getUser(from.changedBy, userRepository.getCurrentUser())
                Message.Content.ChannelIconChanged(
                    changedById = changedBy.id,
                    changedByUsername = changedBy.username,
                    changedByAvatarUrl = changedBy.avatarUrl,
                )
            }
            is MessageDto.ContentDto.ChannelRenamed -> {
                val user = users.getUser(from.renamedBy, userRepository.getCurrentUser())
                Message.Content.ChannelRenamed(
                    name = from.name,
                    renamedById = user.id,
                    renamedByUsername = user.username,
                    renamedByAvatarUrl = user.avatarUrl
                )
            }
            is MessageDto.ContentDto.Message -> Message.Content.Message(content = from.content)
            is MessageDto.ContentDto.Text -> Message.Content.Text(content = from.content)
            is MessageDto.ContentDto.UserAdded -> {
                val addedByUser = users.getUser(from.addedBy, userRepository.getCurrentUser())
                val addedUser = users.getUser(from.addedUserId, userRepository.getCurrentUser())
                Message.Content.UserAdded(
                    addedUserId = addedUser.id,
                    addedUsername = addedUser.username,
                    addedBy = addedByUser.id,
                    addedByUsername = addedByUser.username,
                )
            }
            is MessageDto.ContentDto.UserBanned -> {
                val user = users.getUser(from.userId, userRepository.getCurrentUser())
                Message.Content.UserBanned(
                    userId = user.id,
                    username = user.username,
                    userAvatarUrl = user.avatarUrl
                )
            }
            is MessageDto.ContentDto.UserJoined -> {
                val user = users.getUser(from.userId, userRepository.getCurrentUser())
                Message.Content.UserJoined(
                    userId = user.id,
                    username = user.username,
                    userAvatarUrl = user.avatarUrl,
                )
            }
            is MessageDto.ContentDto.UserKicked -> {
                val user = users.getUser(from.userId, userRepository.getCurrentUser())
                Message.Content.UserKicked(
                    userId = user.id,
                    username = user.username,
                    userAvatarUrl = user.avatarUrl
                )
            }
            is MessageDto.ContentDto.UserLeft -> {
                val user = users.getUser(from.userId, userRepository.getCurrentUser())
                Message.Content.UserLeft(
                    userId = user.id,
                    username = user.username,
                    userAvatarUrl = user.avatarUrl
                )
            }
            is MessageDto.ContentDto.UserRemove -> {
                val removedByUser = users.getUser(from.removedBy, userRepository.getCurrentUser())
                val removedUser = users.getUser(from.removedUserId, userRepository.getCurrentUser())
                Message.Content.UserRemove(
                    removedById = removedByUser.id,
                    removedByUsername = removedByUser.username,
                    removedUserId = removedUser.id,
                    removedUsername = removedUser.username
                )
            }
        }
    }
}