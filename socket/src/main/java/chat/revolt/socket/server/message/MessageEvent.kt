/*
 * Created by Tedo Manvelidze(ted3x) on 2/18/22, 11:47 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/18/22, 10:37 PM
 */

package chat.revolt.socket.server.message

import chat.revolt.domain.models.ContentType
import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User
import chat.revolt.socket.events.Event
import chat.revolt.socket.events.Events
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageEvent(
    @Json(name = "_id")
    val id: String,
    val nonce: String? = null,
    val channel: String,
    val author: String,
    val content: Content,
    val attachments: List<Attachment>?,
    val embeds: List<Any>?,
    val mentions: List<String>?,
    val masquerade: Masquerade?
): Events() {

    override val type: Event = Event.Message

    enum class ContentType {
        Message,

        @Json(name = "text")
        Text,

        @Json(name = "user_added")
        UserAdded,

        @Json(name = "user_remove")
        UserRemove,

        @Json(name = "user_joined")
        UserJoined,

        @Json(name = "user_left")
        UserLeft,

        @Json(name = "user_kicked")
        UserKicked,

        @Json(name = "user_banned")
        UserBanned,

        @Json(name = "channel_renamed")
        ChannelRenamed,

        @Json(name = "channel_description_changed")
        ChannelDescriptionChanged,

        @Json(name = "channel_icon_changed")
        ChannelIconChanged,
    }

    sealed class Content(val type: ContentType) {

        @JsonClass(generateAdapter = true)
        data class Message(val content: String) : Content(ContentType.Message)

        @JsonClass(generateAdapter = true)
        data class Text(val content: String) : Content(ContentType.Text)

        @JsonClass(generateAdapter = true)
        data class UserAdded(
            @Json(name = "id")
            val addedUserId: String,
            @Json(name = "by")
            val addedBy: String
        ) :
            Content(ContentType.UserAdded)

        @JsonClass(generateAdapter = true)
        data class UserRemove(
            @Json(name = "id")
            val removedUserId: String,
            @Json(name = "by")
            val removedBy: String
        ) :
            Content(ContentType.UserRemove)

        @JsonClass(generateAdapter = true)
        data class UserJoined(
            @Json(name = "id")
            val userId: String
        ) : Content(ContentType.UserJoined)

        @JsonClass(generateAdapter = true)
        data class UserLeft(
            @Json(name = "id")
            val userId: String
        ) : Content(ContentType.UserLeft)

        @JsonClass(generateAdapter = true)
        data class UserKicked(
            @Json(name = "id")
            val userId: String
        ) : Content(ContentType.UserKicked)

        @JsonClass(generateAdapter = true)
        data class UserBanned(
            @Json(name = "id")
            val userId: String
        ) : Content(ContentType.UserBanned)

        @JsonClass(generateAdapter = true)
        data class ChannelRenamed(
            val name: String,
            @Json(name = "by")
            val renamedBy: String
        ) :
            Content(ContentType.ChannelRenamed)

        @JsonClass(generateAdapter = true)
        data class ChannelDescriptionChanged(
            @Json(name = "by")
            val changedBy: String) :
            Content(ContentType.ChannelDescriptionChanged)

        @JsonClass(generateAdapter = true)
        data class ChannelIconChanged(
            @Json(name = "by")
            val changedBy: String) :
            Content(ContentType.ChannelIconChanged)
    }

    @JsonClass(generateAdapter = true)
    data class Attachment(
        @Json(name = "_id")
        val id: String?,
        val tag: String,
        val size: String?,
        val filename: String?,
        val metadata: Metadata?,
        @Json(name = "content_type")
        val contentType: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Metadata(
            val value: String?,
            val width: Int?,
            val height: Int?
        )
    }

    @JsonClass(generateAdapter = true)
    data class Masquerade(
        val name: String? = null,
        val avatar: String? = null
    )
}
