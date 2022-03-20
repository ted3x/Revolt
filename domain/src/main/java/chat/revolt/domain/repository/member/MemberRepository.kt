/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 10:52 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 10:52 PM
 */

package chat.revolt.domain.repository.member

import chat.revolt.domain.models.member.Member
import kotlinx.coroutines.flow.Flow

interface MemberRepository {

    suspend fun addMembers(members: List<Member>)
    suspend fun getMember(serverId: String, userId: String): Member?
    fun getMembers(serverId: String): Flow<List<Member>>
}