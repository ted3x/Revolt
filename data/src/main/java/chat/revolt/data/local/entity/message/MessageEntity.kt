/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:59 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:59 AM
 */

package chat.revolt.data.local.entity.message

import androidx.room.Entity
import androidx.room.PrimaryKey
import chat.revolt.domain.models.ContentType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val id: String,
    val channel: String,
    val author: String,
    val content: ContentEntity,
    val edited: String?,
    val createdAt: Long,
    val mentions: List<String>?,
    val replies: List<String>?,
) {

    sealed class ContentEntity(@Json(name = "type") val type: ContentType) {

        @JsonClass(generateAdapter = true)
        data class Message(val content: String) : ContentEntity(ContentType.Message)

        @JsonClass(generateAdapter = true)
        data class Text(val content: String) : ContentEntity(ContentType.Text)

        @JsonClass(generateAdapter = true)
        data class UserAdded(
            val addedUserId: String,
            val addedBy: String
        ) :
            ContentEntity(ContentType.UserAdded)

        @JsonClass(generateAdapter = true)
        data class UserRemove(
            val removedUserId: String,
            val removedBy: String
        ) :
            ContentEntity(ContentType.UserRemove)

        @JsonClass(generateAdapter = true)
        data class UserJoined(
            val userId: String
        ) : ContentEntity(ContentType.UserJoined)

        @JsonClass(generateAdapter = true)
        data class UserLeft(
            val userId: String
        ) : ContentEntity(ContentType.UserLeft)

        @JsonClass(generateAdapter = true)
        data class UserKicked(
            val userId: String
        ) : ContentEntity(ContentType.UserKicked)

        @JsonClass(generateAdapter = true)
        data class UserBanned(
            val userId: String
        ) : ContentEntity(ContentType.UserBanned)

        @JsonClass(generateAdapter = true)
        data class ChannelRenamed(
            val name: String,
            val renamedBy: String
        ) :
            ContentEntity(ContentType.ChannelRenamed)

        @JsonClass(generateAdapter = true)
        data class ChannelDescriptionChanged(
            val changedBy: String
        ) :
            ContentEntity(ContentType.ChannelDescriptionChanged)

        @JsonClass(generateAdapter = true)
        data class ChannelIconChanged(
            val changedBy: String
        ) :
            ContentEntity(ContentType.ChannelIconChanged)
    }
}