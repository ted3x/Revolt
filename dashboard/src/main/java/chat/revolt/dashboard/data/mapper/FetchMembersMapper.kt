/*
 * Created by Tedo Manvelidze(ted3x) on 3/21/22, 12:14 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/21/22, 12:14 AM
 */

package chat.revolt.dashboard.data.mapper

import chat.revolt.core.mapper.RequestResponseMapper
import chat.revolt.dashboard.data.dto.fetch_members.FetchMembersRequestDto
import chat.revolt.dashboard.data.dto.fetch_members.FetchMembersResponseDto
import chat.revolt.data.remote.mappers.member.MemberMapper
import chat.revolt.data.remote.mappers.user.UserMapper
import chat.revolt.dashboard.domain.models.fetch_members.FetchMembersRequest
import chat.revolt.dashboard.domain.models.fetch_members.FetchMembersResponse

class FetchMembersMapper(
    private val memberMapper: MemberMapper,
    private val userMapper: UserMapper
) : RequestResponseMapper<FetchMembersRequestDto, FetchMembersRequest, FetchMembersResponseDto, FetchMembersResponse> {
    override suspend fun mapToRequest(from: FetchMembersRequest): FetchMembersRequestDto {
        return FetchMembersRequestDto(serverId = from.serverId)
    }

    override suspend fun mapToResponse(from: FetchMembersResponseDto): FetchMembersResponse {
        return FetchMembersResponse(
            members = from.members.map { memberMapper.mapToDomain(it) },
            users = from.users.map { userMapper.mapToDomain(it) })
    }
}