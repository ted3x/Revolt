/*
 * Created by Tedo Manvelidze(ted3x) on 3/21/22, 12:18 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/21/22, 12:18 AM
 */

package chat.revolt.dashboard.domain.repository.members

import chat.revolt.core.extensions.ResultWrapper
import chat.revolt.dashboard.domain.models.fetch_members.FetchMembersRequest
import chat.revolt.dashboard.domain.models.fetch_members.FetchMembersResponse
import chat.revolt.domain.models.member.Member
import kotlinx.coroutines.flow.Flow

interface MembersRepository {
    suspend fun addMembers(members: List<Member>)
    suspend fun getMember(serverId: String, userId: String): Member?
    fun getMembers(serverId: String): Flow<List<Member>>
    suspend fun fetchMembers(request: FetchMembersRequest): ResultWrapper<FetchMembersResponse>
}