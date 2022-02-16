/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:45 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:45 AM
 */

package chat.revolt.dashboard.data.data_source

import chat.revolt.core.extensions.awaitResult
import chat.revolt.dashboard.data.ChannelService
import chat.revolt.dashboard.data.dto.FetchMessagesRequestDto
import chat.revolt.dashboard.data.dto.FetchMessagesResponseDto
import chat.revolt.dashboard.data.dto.MessageDto

class ChannelDataSourceImpl(private val service: ChannelService) : ChannelDataSource {
    override suspend fun fetchMessages(
        channelId: String,
        requestDto: FetchMessagesRequestDto
    ): FetchMessagesResponseDto {
        return service.fetchMessages(
            channelId = channelId,
            before = requestDto.before,
            limit = requestDto.limit,
            sort = requestDto.sort,
            includeUsers = requestDto.includeUsers
        ).awaitResult()
    }
}