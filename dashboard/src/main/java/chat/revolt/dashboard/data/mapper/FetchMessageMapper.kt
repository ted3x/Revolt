/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:49 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:49 AM
 */

package chat.revolt.dashboard.data.mapper

import chat.revolt.dashboard.data.dto.fetch_messages.FetchMessagesRequestDto
import chat.revolt.dashboard.data.dto.fetch_messages.FetchMessagesResponseDto
import chat.revolt.dashboard.domain.models.fetch_messages.FetchMessagesRequest
import chat.revolt.dashboard.domain.models.fetch_messages.FetchMessagesResponse
import chat.revolt.data.remote.mappers.member.MemberMapper
import chat.revolt.data.remote.mappers.message.MessageMapper
import chat.revolt.data.remote.mappers.user.UserMapper

class FetchMessageMapper(
    private val userMapper: UserMapper,
    private val messageMapper: MessageMapper,
    private val memberMapper: MemberMapper
) {
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
        val users = from.users.toSet().map { userMapper.mapToDomain(it) }
        val members = from.members?.toSet()?.map { memberMapper.mapToDomain(it) }
        return FetchMessagesResponse(
            messages = from.messages.map { messageMapper.mapToDomain(it, users) },
            users = users,
            members = members
        )
    }
}