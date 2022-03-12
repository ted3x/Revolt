/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 12:53 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 11:00 PM
 */

package chat.revolt.data.remote.mappers.user

import chat.revolt.data.remote.dto.user.UserDto
import chat.revolt.domain.models.User

class UserMapper(
    private val relationsMapper: RelationshipMapper,
    private val statusMapper: StatusMapper,
    private val relationshipStatusMapper: RelationshipStatusMapper,
    private val botMapper: BotMapper,
    private val avatarMapper: AvatarMapper
) {
    fun mapToDomain(from: UserDto): User {
        return User(
            id = from.id,
            username = from.username,
            avatar = from.avatar?.let { avatarMapper.mapToDomain(it) },
            relations = relationsMapper.mapToDomain(from.relations),
            badges = from.badges,
            status = statusMapper.mapToDomain(from.status),
            relationship = from.relationship?.let { relationshipStatusMapper.mapToDomain(it) } ?: User.RelationshipStatus.None,
            online = from.online ?: false,
            flags = from.flags,
            bot = botMapper.mapToDomain(from.bot)
        )
    }

    fun mapToDto(from: User): UserDto {
        return UserDto(
            id = from.id,
            username = from.username,
            avatar = from.avatar?.let { avatarMapper.mapToDto(it) },
            relations = relationsMapper.mapToDto(from.relations),
            badges = from.badges,
            status = statusMapper.mapToDto(from.status),
            relationship = from.relationship.let { relationshipStatusMapper.mapToDto(it) },
            online = from.online,
            flags = from.flags,
            bot = botMapper.mapToDto(from.bot)
        )
    }
}