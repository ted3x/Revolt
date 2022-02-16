/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:49 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:49 AM
 */

package chat.revolt.dashboard.data.mapper

import chat.revolt.core.mapper.ServiceMapper
import chat.revolt.dashboard.data.dto.FetchMessagesRequestDto
import chat.revolt.dashboard.data.dto.FetchMessagesResponseDto
import chat.revolt.dashboard.data.dto.MessageDto
import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.models.FetchMessagesResponse
import chat.revolt.data.remote.dto.user.UserDto
import chat.revolt.data.remote.mappers.user.UserDtoToUserMapper
import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User

class FetchMessageMapper(private val userMapper: UserDtoToUserMapper) {
    fun mapToRequest(from: FetchMessagesRequest): FetchMessagesRequestDto {
        return FetchMessagesRequestDto(
            limit = from.limit,
            before = from.before,
            after = from.after,
            sort = from.sort,
            includeUsers = from.includeUsers
        )
    }

    fun mapToResponse(
        from: FetchMessagesResponseDto,
        selfUser: User
    ): FetchMessagesResponse {
        return FetchMessagesResponse(
            messages = from.messages.map { it.map(from.users, selfUser) },
            users = from.users.map { userMapper.map(it) }
        )
    }

    private fun MessageDto.map(users: List<UserDto>, selfUser: User): Message {
        return Message(
            id = this.id,
            channel = this.channel,
            author = this.author.map(users, selfUser),
            content = this.content,
            attachments = this.attachments?.map { it.map() },
            edited = this.edited?.date,
            mentions = this.mentions,
            replies = this.replies,
            masquerade = Message.Masquerade(this.masquerade?.name, this.masquerade?.avatar)
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

    private fun String.map(users: List<UserDto>, selfUser: User): User {
        return users.firstOrNull { it.id == this }?.let { userMapper.map(it) } ?: selfUser
    }
}