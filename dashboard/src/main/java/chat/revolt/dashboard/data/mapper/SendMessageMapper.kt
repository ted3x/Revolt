/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 4:21 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 4:21 PM
 */

package chat.revolt.dashboard.data.mapper

import chat.revolt.core.mapper.RequestResponseMapper
import chat.revolt.dashboard.data.dto.send_message.SendMessageRequestDto
import chat.revolt.dashboard.domain.models.send_message.SendMessageRequest
import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.data.remote.mappers.message.AttachmentMapper
import chat.revolt.data.remote.mappers.message.MasqueradeMapper
import chat.revolt.data.remote.mappers.message.MessageMapper
import chat.revolt.data.remote.mappers.user.UserMapper
import chat.revolt.domain.models.Message


class SendMessageMapper(
    private val messageMapper: MessageMapper,
    private val attachmentMapper: AttachmentMapper,
    private val masqueradeMapper: MasqueradeMapper
): RequestResponseMapper<SendMessageRequestDto, SendMessageRequest, MessageDto, Message> {

    override suspend fun mapToRequest(from: SendMessageRequest): SendMessageRequestDto {
        return SendMessageRequestDto(
            channelId = from.channelId,
            content = from.content.content,
            attachments = from.attachments?.map { attachmentMapper.mapToDto(it) },
            replies = from.replies?.map { it.map() },
            masquerade = from.masquerade?.let { masqueradeMapper.mapToDto(it) }
        )
    }

    private fun SendMessageRequest.SendMessageReply.map(): SendMessageRequestDto.SendMessageReplyDto {
        return SendMessageRequestDto.SendMessageReplyDto(
            id = this.id,
            mention = this.mention
        )
    }

    override suspend fun mapToResponse(from: MessageDto): Message {
        return messageMapper.mapToDomain(from, null)
    }
}