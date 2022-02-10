/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 8:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 8:42 PM
 */

package chat.revolt.data.local.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val username: String,
    val avatarUrl: String?,
    val backgroundUrl: String?,
    val relations: List<Relationship>?,
    val badges: Int?,
    val status: Status?,
    val relationship: RelationshipStatus = RelationshipStatus.None,
    val online: Boolean = false,
    val flags: Int?,
    val bot: Bot?
) {

    data class Relationship(val userId: String, val status: RelationshipStatus)

    enum class RelationshipStatus {
        Blocked,
        BlockedOther,
        Friend,
        Incoming,
        None,
        Outgoing,
        User
    }

    data class Status(val text: String, val presence: Presence)

    enum class Presence {
        Busy,
        Idle,
        Invisible,
        Online
    }

    data class Bot(val ownerId: String)
}