/*
 * Created by Tedo Manvelidze(ted3x) on 3/21/22, 12:21 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/21/22, 12:21 AM
 */

package chat.revolt.dashboard.data.repository.members

import chat.revolt.core.extensions.ResultWrapper
import chat.revolt.core.extensions.safeApiCall
import chat.revolt.dashboard.data.data_source.server.ServerDataSource
import chat.revolt.dashboard.data.mapper.FetchMembersMapper
import chat.revolt.dashboard.domain.repository.members.MembersRepository
import chat.revolt.dashboard.domain.models.fetch_members.FetchMembersRequest
import chat.revolt.dashboard.domain.models.fetch_members.FetchMembersResponse
import chat.revolt.data.local.dao.MemberDao
import chat.revolt.data.local.mappers.member.MemberEntityMapper
import chat.revolt.domain.models.member.Member
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class MembersRepositoryImpl(
    private val serverDataSource: ServerDataSource,
    private val fetchMembersMapper: FetchMembersMapper,
    private val memberDao: MemberDao,
    private val memberEntityMapper: MemberEntityMapper

) : MembersRepository {

    override suspend fun addMembers(members: List<Member>) {
        memberDao.addMembers(memberEntityMapper.mapToEntity(members))
    }

    override suspend fun getMember(serverId: String, userId: String): Member? {
        return memberDao.getMember(serverId, userId)?.let { memberEntityMapper.mapToDomain(it) }
    }

    override fun getMembers(serverId: String): Flow<List<Member>> {
        return memberDao.getMembers(serverId).mapLatest { memberEntityMapper.mapToDomain(it) }
    }
    override suspend fun fetchMembers(request: FetchMembersRequest): ResultWrapper<FetchMembersResponse> {
        return safeApiCall {
            fetchMembersMapper.mapToResponse(
                serverDataSource.fetchMembers(
                    fetchMembersMapper.mapToRequest(
                        request
                    )
                )
            )
        }
    }
}