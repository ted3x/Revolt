/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 10:45 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 10:45 PM
 */

package chat.revolt.data.remote.mappers.member

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.remote.dto.member.MemberDto
import chat.revolt.data.remote.mappers.message.AttachmentMapper
import chat.revolt.domain.models.member.Member

class MemberMapper(private val attachmentMapper: AttachmentMapper) {

    fun mapToDomain(from: MemberDto): Member {
        return Member(
            id = Member.Identifier(from.id.serverId, from.id.userId),
            nickname = from.nickname,
            avatar = from.avatar?.let { attachmentMapper.mapToDomain(it) },
            roles = from.roles
        )
    }
}