/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:02 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:02 AM
 */

package chat.revolt.data.local.mappers

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.local.entity.user.UserEntity
import chat.revolt.domain.models.User

class UserEntityToUserMapper(
    private val relationsMapper: RelationsEntityToRelationsMapper,
    private val statusMapper: StatusEntityToStatusMapper,
    private val relationshipStatusMapper: RelationshipStatusMapper,
    private val botMapper: BotEntityToBotMapper
) :
    Mapper<UserEntity, User> {
    override fun map(from: UserEntity): User {
        return User(
            id = from.id,
            username = from.username,
            avatarUrl = from.avatarUrl,
            backgroundUrl = from.backgroundUrl,
            relations = relationsMapper.map(from.relations),
            badges = from.badges,
            status = from.status?.let { statusMapper.map(it) } ?: User.Status.NOT_LOADED,
            relationship = relationshipStatusMapper.map(from.relationship),
            online = from.online,
            flags = from.flags,
            bot = from.bot?.let { botMapper.map(it) }
        )
    }
}