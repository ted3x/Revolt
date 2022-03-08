/*
 * Created by Tedo Manvelidze(ted3x) on 2/18/22, 11:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/18/22, 11:53 PM
 */

package chat.revolt.socket.server.message

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User
import chat.revolt.domain.repository.UserRepository

class MessageEventMapper(private val userRepository: UserRepository): Mapper<MessageEvent, Message> {
    override suspend fun map(from: MessageEvent): Message {
        val user = userRepository.getUser(from.author)
        return Message(
            id = from.id,
            channel = from.channel,
            author = user,
            content = from.content.map(user),
            attachments = from.attachments?.map { it.map() },
            edited = null,
            mentions = from.mentions?.let { userRepository.getUsers(it) },
            replies = null,
            masquerade = Message.Masquerade(from.masquerade?.name, from.masquerade?.avatar)
        )
    }

    //TODO("remove list")
    private suspend fun MessageEvent.Content.map(user: User): Message.Content {
        return when (this) {
            is MessageEvent.Content.ChannelDescriptionChanged -> Message.Content.ChannelDescriptionChanged(changedBy = user)
            is MessageEvent.Content.ChannelIconChanged -> Message.Content.ChannelIconChanged(user)

            is MessageEvent.Content.ChannelRenamed -> Message.Content.ChannelRenamed(
                name = this.name,
                renamedBy = user
            )
            is MessageEvent.Content.Message -> Message.Content.Message(content = this.content)
            is MessageEvent.Content.Text -> Message.Content.Text(content = this.content)
            is MessageEvent.Content.UserAdded -> Message.Content.UserAdded(
                addedUser = user,
                addedBy = userRepository.getUser(this.addedBy)
            )
            is MessageEvent.Content.UserBanned -> Message.Content.UserBanned(user = user)
            is MessageEvent.Content.UserJoined -> Message.Content.UserJoined(user = user)
            is MessageEvent.Content.UserKicked -> Message.Content.UserKicked(user = user)
            is MessageEvent.Content.UserLeft -> Message.Content.UserLeft(user = user)
            is MessageEvent.Content.UserRemove -> Message.Content.UserRemove(
                removedBy = userRepository.getMessageAuthor(this.removedUserId, listOf()),
                removedUser = user
            )
        }
    }

    private fun MessageEvent.Attachment.map(): Message.Attachment {
        return Message.Attachment(
            id = this.id,
            tag = this.tag,
            size = this.size,
            filename = this.filename,
            metadata = this.metadata?.map(),
            contentType = this.contentType
        )
    }

    private fun MessageEvent.Attachment.Metadata.map(): Message.Attachment.Metadata {
        return Message.Attachment.Metadata(
            value = this.value,
            width = this.width,
            height = this.height
        )
    }
}