/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:45 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:45 AM
 */

package chat.revolt.dashboard.data.data_source

import chat.revolt.core.extensions.awaitResult
import chat.revolt.dashboard.data.ChannelService
import chat.revolt.dashboard.data.dto.fetch_messages.FetchMessagesRequestDto
import chat.revolt.dashboard.data.dto.fetch_messages.FetchMessagesResponseDto
import chat.revolt.dashboard.data.dto.send_message.SendMessageRequestDto
import chat.revolt.data.remote.dto.message.MessageDto

class ChannelDataSourceImpl(private val service: ChannelService) : ChannelDataSource {
    override suspend fun fetchMessages(
        request: FetchMessagesRequestDto
    ): FetchMessagesResponseDto {
        return service.fetchMessages(
            channelId = request.channelId,
            before = request.before,
            limit = request.limit,
            sort = request.sort,
            includeUsers = request.includeUsers
        ).awaitResult()
    }

    override suspend fun sendMessage(request: SendMessageRequestDto): MessageDto {
        return service.sendMessage(
            channelId = request.channelId,
            content = request.content,
            attachments = request.attachments,
            replies = request.replies,
            masquerade = request.masquerade
        ).awaitResult()
    }
}