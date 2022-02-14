/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 8:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 8:42 PM
 */

package chat.revolt.data.local.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "user")
@JsonClass(generateAdapter = true)
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

    @JsonClass(generateAdapter = true)
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

    @JsonClass(generateAdapter = true)
    data class Status(val text: String, val presence: Presence)

    enum class Presence {
        Busy,
        Idle,
        Invisible,
        Online
    }
    @JsonClass(generateAdapter = true)
    data class Bot(val ownerId: String)
}