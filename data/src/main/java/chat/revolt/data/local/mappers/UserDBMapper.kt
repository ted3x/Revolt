/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 1:02 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 1:02 AM
 */

package chat.revolt.data.local.mappers

import chat.revolt.core.mapper.EntityMapper
import chat.revolt.core.mapper.Mapper
import chat.revolt.data.local.entity.user.UserEntity
import chat.revolt.domain.models.User
import chat.revolt.domain.models.User.Status.Companion.NOT_LOADED

class UserDBMapper : EntityMapper<UserEntity, User> {

    override fun mapToDomain(from: UserEntity): User {
        return User(
            id = from.id,
            username = from.username,
            avatarUrl = from.avatarUrl,
            backgroundUrl = from.backgroundUrl,
            relations = from.relations?.map { it.map() },
            badges = from.badges,
            status = from.status?.map() ?: NOT_LOADED,
            relationship = from.relationship.map(),
            online = from.online,
            flags = from.flags,
            bot = from.bot?.map()
        )
    }

    private fun UserEntity.Presence.map(): User.Presence {
        return User.Presence.valueOf(this.name)
    }

    private fun UserEntity.Status.map(): User.Status {
        return User.Status(text = this.text, presence = this.presence.map())
    }

    private fun UserEntity.RelationshipStatus.map(): User.RelationshipStatus {
        return User.RelationshipStatus.valueOf(this.name)
    }

    fun UserEntity.Relationship.map(): User.Relationship {
        return User.Relationship(userId = this.userId, status = this.status.map())
    }

    private fun UserEntity.Bot.map(): User.Bot {
        return User.Bot(ownerId = this.ownerId)
    }

    override fun mapToEntity(from: User): UserEntity {
        return UserEntity(
            id = from.id,
            username = from.username,
            avatarUrl = from.avatarUrl,
            backgroundUrl = from.backgroundUrl,
            relations = from.relations?.map { it.map() },
            badges = from.badges,
            status = from.status.map(),
            relationship = from.relationship.map(),
            online = from.online,
            flags = from.flags,
            bot = from.bot?.map()
        )
    }

    private fun User.Presence.map(): UserEntity.Presence {
        return UserEntity.Presence.valueOf(this.name)
    }

    private fun User.Status.map(): UserEntity.Status {
        return UserEntity.Status(text = this.text, presence = this.presence.map())
    }

    private fun User.RelationshipStatus.map(): UserEntity.RelationshipStatus {
        return UserEntity.RelationshipStatus.valueOf(this.name)
    }

    private fun User.Relationship.map(): UserEntity.Relationship {
        return UserEntity.Relationship(userId = this.userId, status = this.status.map())
    }

    private fun User.Bot.map(): UserEntity.Bot {
        return UserEntity.Bot(ownerId = this.ownerId)
    }
}