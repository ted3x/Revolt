/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:49 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:49 AM
 */

package chat.revolt.dashboard.data.mapper

import chat.revolt.dashboard.data.dto.FetchMessagesRequestDto
import chat.revolt.dashboard.data.dto.FetchMessagesResponseDto
import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.models.FetchMessagesResponse
import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.data.remote.dto.user.UserDto
import chat.revolt.data.remote.mappers.message.MessageMapperDto
import chat.revolt.data.remote.mappers.user.UserDtoToUserMapper
import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User

class FetchMessageMapper(private val userMapper: UserDtoToUserMapper, private val messageMapper: MessageMapperDto) {
    fun mapToRequest(from: FetchMessagesRequest): FetchMessagesRequestDto {
        return FetchMessagesRequestDto(
            channelId = from.channelId,
            limit = from.limit,
            before = from.before,
            after = from.after,
            sort = from.sort,
            includeUsers = from.includeUsers
        )
    }

    suspend fun mapToResponse(from: FetchMessagesResponseDto): FetchMessagesResponse {
        val users = from.users.toSet().map { userMapper.map(it) }
        return FetchMessagesResponse(
            messages = from.messages.map { messageMapper.map(it, users) },
            users = users
        )
    }
}