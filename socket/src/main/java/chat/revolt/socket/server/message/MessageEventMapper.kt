/*
 * Created by Tedo Manvelidze(ted3x) on 2/18/22, 11:53 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/18/22, 11:53 PM
 */

package chat.revolt.socket.server.message

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.domain.models.Message
import chat.revolt.domain.repository.UserRepository

class MessageEventMapper(private val userRepository: UserRepository): Mapper<MessageEvent, Message> {
    override suspend fun map(from: MessageEvent): Message {
        return Message(
            id = from.id,
            channel = from.channel,
            author = userRepository.getUser(from.author),
            content = from.content,
            attachments = from.attachments?.map { it.map() },
            edited = null,
            mentions = from.mentions?.map { userRepository.getUser(userId = it) },
            replies = null,
            masquerade = Message.Masquerade(from.masquerade?.name, from.masquerade?.avatar)
        )
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