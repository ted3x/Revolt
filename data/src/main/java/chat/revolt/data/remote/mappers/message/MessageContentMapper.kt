/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 4:32 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 4:32 PM
 */

package chat.revolt.data.remote.mappers.message

import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.data.remote.mappers.user.UserMapper
import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User
import chat.revolt.domain.repository.UserRepository

class MessageContentMapper(private val userRepository: UserRepository) {

    fun mapToDto(from: Message.Content): MessageDto.ContentDto {
        return when (from) {
            is Message.Content.ChannelDescriptionChanged -> MessageDto.ContentDto.ChannelDescriptionChanged(
                changedBy = from.changedBy.id
            )
            is Message.Content.ChannelIconChanged -> MessageDto.ContentDto.ChannelIconChanged(
                changedBy = from.changedBy.id
            )
            is Message.Content.ChannelRenamed -> MessageDto.ContentDto.ChannelRenamed(
                name = from.name,
                renamedBy = from.renamedBy.id
            )
            is Message.Content.Message -> MessageDto.ContentDto.Message(content = from.content)
            is Message.Content.Text -> MessageDto.ContentDto.Text(content = from.content)
            is Message.Content.UserAdded -> MessageDto.ContentDto.UserAdded(
                addedUserId = from.addedUser.id,
                addedBy = from.addedBy.id
            )
            is Message.Content.UserBanned -> MessageDto.ContentDto.UserBanned(userId = from.user.id)
            is Message.Content.UserJoined -> MessageDto.ContentDto.UserJoined(userId = from.user.id)
            is Message.Content.UserKicked -> MessageDto.ContentDto.UserKicked(userId = from.user.id)
            is Message.Content.UserLeft -> MessageDto.ContentDto.UserLeft(userId = from.user.id)
            is Message.Content.UserRemove -> MessageDto.ContentDto.UserRemove(
                removedBy = from.removedBy.id,
                removedUserId = from.removedUser.id
            )
        }

    }

    suspend fun mapToDomain(from: MessageDto.ContentDto, users: List<User>?): Message.Content {
        return when (from) {
            is MessageDto.ContentDto.ChannelDescriptionChanged -> Message.Content.ChannelDescriptionChanged(
                changedBy = userRepository.getMessageAuthor(from.changedBy, users)
            )
            is MessageDto.ContentDto.ChannelIconChanged -> Message.Content.ChannelIconChanged(
                changedBy = userRepository.getMessageAuthor(from.changedBy, users)
            )
            is MessageDto.ContentDto.ChannelRenamed -> Message.Content.ChannelRenamed(
                name = from.name,
                renamedBy = userRepository.getMessageAuthor(from.renamedBy, users)
            )
            is MessageDto.ContentDto.Message -> Message.Content.Message(content = from.content)
            is MessageDto.ContentDto.Text -> Message.Content.Text(content = from.content)
            is MessageDto.ContentDto.UserAdded -> Message.Content.UserAdded(
                addedUser = userRepository.getMessageAuthor(from.addedUserId, users),
                addedBy = userRepository.getMessageAuthor(from.addedBy, users)
            )
            is MessageDto.ContentDto.UserBanned -> Message.Content.UserBanned(
                user = userRepository.getMessageAuthor(
                    from.userId,
                    users
                )
            )
            is MessageDto.ContentDto.UserJoined -> Message.Content.UserJoined(
                user = userRepository.getMessageAuthor(
                    from.userId,
                    users
                )
            )
            is MessageDto.ContentDto.UserKicked -> Message.Content.UserKicked(
                user = userRepository.getMessageAuthor(
                    from.userId,
                    users
                )
            )
            is MessageDto.ContentDto.UserLeft -> Message.Content.UserLeft(
                user = userRepository.getMessageAuthor(
                    from.userId,
                    users
                )
            )
            is MessageDto.ContentDto.UserRemove -> Message.Content.UserRemove(
                removedBy = userRepository.getMessageAuthor(from.removedUserId, users),
                removedUser = userRepository.getMessageAuthor(from.removedUserId, users)
            )
        }
    }
}