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
    private val userRepository: UserRepository
) {

    suspend fun map(from: MessageDto): Message {
        return Message(
            id = from.id,
            channel = from.channel,
            author = from.author.map(),
            content = from.content,
            attachments = from.attachments?.map { it.map() },
            edited = from.edited?.date,
            mentions = from.mentions,
            replies = from.replies,
            masquerade = Message.Masquerade(from.masquerade?.name, from.masquerade?.avatar)
        )
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

    private suspend fun String.map(): User {
        return userRepository.getUser(this) ?: throw IllegalStateException("user with $this id not found")
    }
}