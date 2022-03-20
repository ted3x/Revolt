/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 10:52 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 10:52 PM
 */

package chat.revolt.data.repository.member

import chat.revolt.data.local.dao.MemberDao
import chat.revolt.data.local.mappers.member.MemberEntityMapper
import chat.revolt.domain.models.member.Member
import chat.revolt.domain.repository.member.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class MemberRepositoryImpl(
    private val memberDao: MemberDao,
    private val memberEntityMapper: MemberEntityMapper
) : MemberRepository {
    override suspend fun addMembers(members: List<Member>) {
        memberDao.addMembers(memberEntityMapper.mapToEntity(members))
    }

    override suspend fun getMember(serverId: String, userId: String): Member? {
        return memberDao.getMember(serverId, userId)?.let { memberEntityMapper.mapToDomain(it) }
    }

    override fun getMembers(serverId: String): Flow<List<Member>> {
        return memberDao.getMembers(serverId).mapLatest { memberEntityMapper.mapToDomain(it) }
    }
}