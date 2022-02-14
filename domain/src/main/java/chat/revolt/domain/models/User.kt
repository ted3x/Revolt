/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 4:33 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 4:33 PM
 */

package chat.revolt.domain.models

data class User(
    val id: String,
    val username: String,
    var avatarUrl: String?,
    var backgroundUrl: String? = null,
    val relations: List<Relationship>? = null,
    val badges: Int? = null,
    val status: Status,
    val relationship: RelationshipStatus,
    val online: Boolean,
    val flags: Int? = null,
    val bot: Bot? = null
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

    data class Status(val text: String, val presence: Presence) {

        companion object {
            val NOT_LOADED = Status(text = "", presence = Presence.Idle)
        }
    }

    enum class Presence {
        Busy,
        Idle,
        Invisible,
        Online
    }

    data class Bot(val ownerId: String)
}