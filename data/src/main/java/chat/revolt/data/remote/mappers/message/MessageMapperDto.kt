/*
 * Created by Tedo Manvelidze(ted3x) on 2/17/22, 10:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/17/22, 10:46 PM
 */

package chat.revolt.data.remote.mappers.message

import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User
import chat.revolt.domain.repository.UserRepository

class MessageMapperDto(
    private val userRepository: UserRepository,
) {

    suspend fun map(from: MessageDto, users: List<User>): Message {
        return Message(
            id = from.id,
            channel = from.channel,
            author = userRepository.getMessageAuthor(from.author, users),
            content = from.content.map(users),
            attachments = from.attachments?.map { it.map() },
            edited = from.edited?.date,
            mentions = from.mentions?.let { userRepository.getUsers(it) },
            replies = from.replies,
            masquerade = Message.Masquerade(from.masquerade?.name, from.masquerade?.avatar)
        )
    }

    private suspend fun MessageDto.ContentDto.map(users: List<User>): Message.Content {
        return when (this) {
            is MessageDto.ContentDto.ChannelDescriptionChanged -> Message.Content.ChannelDescriptionChanged(
                changedBy = userRepository.getMessageAuthor(this.changedBy, users)
            )
            is MessageDto.ContentDto.ChannelIconChanged -> Message.Content.ChannelIconChanged(
                changedBy = userRepository.getMessageAuthor(this.changedBy, users)
            )
            is MessageDto.ContentDto.ChannelRenamed -> Message.Content.ChannelRenamed(
                name = this.name,
                renamedBy = userRepository.getMessageAuthor(this.renamedBy, users)
            )
            is MessageDto.ContentDto.Message -> Message.Content.Message(content = this.content)
            is MessageDto.ContentDto.Text -> Message.Content.Text(content = this.content)
            is MessageDto.ContentDto.UserAdded -> Message.Content.UserAdded(
                addedUser = userRepository.getMessageAuthor(this.addedUserId, users),
                addedBy = userRepository.getMessageAuthor(this.addedBy, users)
            )
            is MessageDto.ContentDto.UserBanned -> Message.Content.UserBanned(
                user = userRepository.getMessageAuthor(
                    this.userId,
                    users
                )
            )
            is MessageDto.ContentDto.UserJoined -> Message.Content.UserJoined(
                user = userRepository.getMessageAuthor(
                    this.userId,
                    users
                )
            )
            is MessageDto.ContentDto.UserKicked -> Message.Content.UserKicked(
                user = userRepository.getMessageAuthor(
                    this.userId,
                    users
                )
            )
            is MessageDto.ContentDto.UserLeft -> Message.Content.UserLeft(
                user = userRepository.getMessageAuthor(
                    this.userId,
                    users
                )
            )
            is MessageDto.ContentDto.UserRemove -> Message.Content.UserRemove(
                removedBy = userRepository.getMessageAuthor(this.removedUserId, users),
                removedUser = userRepository.getMessageAuthor(this.removedUserId, users)
            )
        }
    }

    private fun MessageDto.AttachmentDto.map(): Message.Attachment {
        return Message.Attachment(
            id = this.id,
            tag = this.tag,
            size = this.size,
            filename = this.filename,
            metadata = this.metadata?.map(),
            contentType = this.contentType
        )
    }

    private fun MessageDto.AttachmentDto.MetadataDto.map(): Message.Attachment.Metadata {
        return Message.Attachment.Metadata(
            value = this.value,
            width = this.width,
            height = this.height
        )
    }

}