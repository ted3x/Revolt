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
import chat.revolt.domain.models.Message

class FetchMessageMapper :
    ServiceMapper<FetchMessagesRequestDto, FetchMessagesRequest, List<MessageDto>, List<Message>> {
    override fun mapToRequest(from: FetchMessagesRequest): FetchMessagesRequestDto {
        return FetchMessagesRequestDto(
            limit = from.limit,
            before = from.before,
            after = from.after,
            sort = from.sort,
            includeUsers = from.includeUsers
        )
    }

    override fun mapToResponse(from: List<MessageDto>): List<Message> {
        return from.map { it.map() }
    }

    private fun MessageDto.map(): Message {
        return Message(
            id = this.id,
            channel = this.channel,
            author = this.author,
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
}