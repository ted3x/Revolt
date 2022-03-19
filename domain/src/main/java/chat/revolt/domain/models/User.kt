/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 4:33 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 4:33 PM
 */

package chat.revolt.domain.models

import chat.revolt.domain.R

data class User(
    val id: String,
    val username: String,
    var avatar: Avatar?,
    var backgroundUrl: String? = null,
    val relations: List<Relationship>? = null,
    val badges: Int? = null,
    val status: Status,
    val relationship: RelationshipStatus,
    val online: Boolean,
    val flags: Int? = null,
    val bot: Bot? = null
) {

    val isSystemUser = id == SYSTEM_USER_ID
    val avatarUrl
        get() =
            if (avatar == null) "$DEFAULT_AVATAR_BASE_URL/$id/default_avatar"
            else AVATAR_BASE_URL + avatar!!.id

    data class Avatar(
        val id: String,
        val tag: String,
        val size: String,
        val filename: String,
        val metadata: Metadata,
        val contentType: String
    )

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

    enum class Presence(val color: String) {
        Busy(color = "#FF0000"),
        Idle(color = "#FF0000"),
        Invisible(color = "#FF0000"),
        Online(color = "#FF0000")
    }

    data class Bot(val ownerId: String)

    companion object {
        private const val SYSTEM_USER_ID = "00000000000000000000000000"
        private const val AVATAR_BASE_URL = "https://autumn.revolt.chat/avatars/"
        private const val DEFAULT_AVATAR_BASE_URL = "https://api.revolt.chat/users"
        val EMPTY = User(
            id = "",
            username = "",
            avatar = null,
            status = Status("", Presence.Idle),
            relationship = RelationshipStatus.User,
            online = false
        )
    }
}


fun List<User>?.getUser(userId: String, currentUser: User) =
    if (userId == currentUser.id) currentUser else this?.firstOrNull { it.id == userId }
        ?: throw IllegalStateException("User with $userId id not found")