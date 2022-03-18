/*
 * Created by Tedo Manvelidze(ted3x) on 2/17/22, 10:50 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/17/22, 10:46 PM
 */

package chat.revolt.data.remote.mappers.message

import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User
import chat.revolt.domain.models.getUser
import chat.revolt.domain.repository.UserRepository

class MessageMapper(
    private val userRepository: UserRepository,
    private val messageContentMapper: MessageContentMapper,
    private val attachmentMapper: AttachmentMapper,
    private val masqueradeMapper: MasqueradeMapper
) {

    suspend fun mapToDomain(from: MessageDto, users: List<User>?): Message {
        val author = users.getUser(from.author, userRepository.getCurrentUser())
        return Message(
            id = from.id,
            channel = from.channel,
            authorId = from.author,
            authorName = author.username,
            authorAvatarUrl = author.avatarUrl,
            content = messageContentMapper.mapToDomain(from.content, users),
            attachments = from.attachments?.map { attachmentMapper.mapToDomain(it) },
            edited = from.edited?.date,
            mentions = from.mentions,
            replies = from.replies,
            masquerade = from.masquerade?.let { masqueradeMapper.mapToDomain(it) }
        )
    }

    fun mapToDto(from: Message): MessageDto {
        return MessageDto(
            id = from.id,
            channel = from.channel,
            author = from.authorId,
            content = messageContentMapper.mapToDto(from.content),
            attachments = from.attachments?.map { attachmentMapper.mapToDto(it) },
            edited = from.edited?.let { MessageDto.EditedDto(it) },
            embeds = listOf(),
            mentions = from.mentions,
            replies = from.replies,
            masquerade = from.masquerade?.let { masqueradeMapper.mapToDto(it) }
        )
    }
}