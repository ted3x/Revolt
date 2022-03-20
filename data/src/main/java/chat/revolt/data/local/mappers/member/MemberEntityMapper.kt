/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 10:54 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 10:54 PM
 */

package chat.revolt.data.local.mappers.member

import chat.revolt.data.local.entity.member.MemberEntity
import chat.revolt.data.local.mappers.AttachmentEntityMapper
import chat.revolt.data.local.mappers.EntityDomainMapper
import chat.revolt.domain.models.member.Member

class MemberEntityMapper(private val attachmentEntityMapper: AttachmentEntityMapper) :
    EntityDomainMapper<MemberEntity, Member> {
    override fun mapToDomain(from: MemberEntity): Member {
        return Member(
            id = Member.Identifier(serverId = from.serverId, userId = from.userId),
            nickname = from.nickname,
            avatar = from.avatar?.let { attachmentEntityMapper.mapToDomain(it) },
            roles = from.roles
        )
    }

    override fun mapToEntity(from: Member): MemberEntity {
        return MemberEntity(
            userId = from.id.userId,
            serverId = from.id.serverId,
            nickname = from.nickname,
            avatar = from.avatar?.let {
                attachmentEntityMapper.mapToEntity(it)
            },
            roles = from.roles
        )
    }
}