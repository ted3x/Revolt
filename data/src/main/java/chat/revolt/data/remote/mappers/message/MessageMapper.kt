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

class MessageMapper(
    private val messageContentMapper: MessageContentMapper,
    private val userRepository: UserRepository,
    private val attachmentMapper: AttachmentMapper,
    private val masqueradeMapper: MasqueradeMapper
) {

    suspend fun mapToDomain(from: MessageDto, users: List<User>?): Message {
        return Message(
            id = from.id,
            channel = from.channel,
            author = userRepository.getMessageAuthor(from.author, users),
            content = messageContentMapper.mapToDomain(from.content, users),
            attachments = from.attachments?.map { attachmentMapper.mapToDomain(it) },
            edited = from.edited?.date,
            mentions = from.mentions?.let { userRepository.getUsers(it) },
            replies = from.replies,
            masquerade = from.masquerade?.let { masqueradeMapper.mapToDomain(it) }
        )
    }

    fun mapToDto(from: Message): MessageDto {
        return MessageDto(
            id = from.id,
            channel = from.channel,
            author = from.author.id,
            content = messageContentMapper.mapToDto(from.content),
            attachments = from.attachments?.map { attachmentMapper.mapToDto(it) },
            edited = from.edited?.let { MessageDto.EditedDto(it) },
            embeds = listOf(),
            mentions = from.mentions?.map { it.id },
            replies = from.replies,
            masquerade = from.masquerade?.let { masqueradeMapper.mapToDto(it) }
        )
    }
}