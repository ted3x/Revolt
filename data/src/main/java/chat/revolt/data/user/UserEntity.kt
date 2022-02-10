/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 4:33 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 4:33 PM
 */

package chat.revolt.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val username: String,
    val avatar: Avatar?,
    val relations: List<Relationship>?,
    val badges: Int?,
    val status: UserStatus?,
    val relationship: RelationshipStatus = RelationshipStatus.None,
    val online: Boolean = false,
    val flags: Int?,
    val bot: Bot?
) {
    data class Avatar(
        val id: String,
        val tag: String,
        val size: Long,
        val filename: String,
        val metadata: Metadata,
        val contentType: String
    )

    enum class AvatarTag(val value: String) {
        Attachments(value = "attachments"),
        Avatars(value = "avatars"),
        Backgrounds(value = "backgrounds"),
        Banners(value = "banners"),
        Icons(value = "icons")
    }

    interface Metadata {
        val value: String
    }

    interface TwoDimensionalMetadata : Metadata {
        val width: Long
        val height: Long
    }

    class FileMetadata : Metadata {
        override val value: String
            get() = "File"
    }

    class TextMetadata : Metadata {
        override val value: String
            get() = "Text"
    }

    class AudioMetadata : Metadata {
        override val value: String
            get() = "Audio"
    }

    class ImageMetadata(override val width: Long, override val height: Long) :
        TwoDimensionalMetadata {
        override val value: String
            get() = "Image"
    }

    class VideoMetadata(override val width: Long, override val height: Long) :
        TwoDimensionalMetadata {
        override val value: String
            get() = "Video"
    }

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

    data class UserStatus(val text: String, val presence: UserPresence)
    enum class UserPresence(val value: String) {
        Busy(value = "busy"),
        Idle(value = "idle"),
        Invisible(value = "invisible"),
        Online(value = "online")
    }

    data class Bot(val ownerId: String)
}