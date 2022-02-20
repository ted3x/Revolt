/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 12:53 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 11:00 PM
 */

package chat.revolt.data.remote.mappers.user

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.remote.dto.user.UserDto
import chat.revolt.domain.models.User

class UserDtoToUserMapper(
    private val relationsMapper: RelationshipDtoToRelationshipMapper,
    private val statusMapper: StatusDtoToStatusMapper,
    private val relationshipStatusMapper: RelationshipStatusMapper,
    private val botMapper: BotDtoToBotMapper
) : Mapper<UserDto, User> {
    override suspend fun map(from: UserDto): User {
        return User(
            id = from.id,
            username = from.username,
            avatarUrl = from.avatar?.getAvatarUrl() ?: getDefaultAvatarUrl(from.id) ,
            relations = relationsMapper.map(from.relations),
            badges = from.badges,
            status = statusMapper.map(from.status),
            relationship = from.relationship?.let { relationshipStatusMapper.map(it) } ?: User.RelationshipStatus.None,
            online = from.online ?: false,
            flags = from.flags,
            bot = botMapper.map(from.bot)
        )
    }

    private fun UserDto.AvatarDto?.getAvatarUrl() = AVATAR_BASE_URL + this?.id

    private fun getDefaultAvatarUrl(userId: String) = "$DEFAULT_AVATAR_BASE_URL/$userId/default_avatar"
    companion object {
        private const val AVATAR_BASE_URL = "https://autumn.revolt.chat/avatars/"
        private const val DEFAULT_AVATAR_BASE_URL = "https://api.revolt.chat/users"
    }
}