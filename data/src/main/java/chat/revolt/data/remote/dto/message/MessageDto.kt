/*
 * Created by Tedo Manvelidze(ted3x) on 2/17/22, 9:56 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/17/22, 9:56 PM
 */

package chat.revolt.data.remote.dto.message

import chat.revolt.data.remote.dto.MetadataDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageDto(
    @Json(name = "_id")
    val id: String,
    val nonce: String? = null,
    val channel: String,
    val author: String,
    val content: ContentDto,
    val attachments: List<AttachmentDto>?,
    val edited: EditedDto?,
    val embeds: List<Any>?,
    val mentions: List<String>?,
    val replies: List<String>?,
    val masquerade: MasqueradeDto?
) {

    enum class ContentTypeDto {
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

    sealed class ContentDto(val type: ContentTypeDto) {

        @JsonClass(generateAdapter = true)
        data class Message(val content: String) : ContentDto(ContentTypeDto.Message)

        @JsonClass(generateAdapter = true)
        data class Text(val content: String) : ContentDto(ContentTypeDto.Text)

        @JsonClass(generateAdapter = true)
        data class UserAdded(
            @Json(name = "id")
            val addedUserId: String,
            @Json(name = "by")
            val addedBy: String
        ) :
            ContentDto(ContentTypeDto.UserAdded)

        @JsonClass(generateAdapter = true)
        data class UserRemove(
            @Json(name = "id")
            val removedUserId: String,
            @Json(name = "by")
            val removedBy: String
        ) :
            ContentDto(ContentTypeDto.UserRemove)

        @JsonClass(generateAdapter = true)
        data class UserJoined(
            @Json(name = "id")
            val userId: String
        ) : ContentDto(ContentTypeDto.UserJoined)

        @JsonClass(generateAdapter = true)
        data class UserLeft(
            @Json(name = "id")
            val userId: String
        ) : ContentDto(ContentTypeDto.UserLeft)

        @JsonClass(generateAdapter = true)
        data class UserKicked(
            @Json(name = "id")
            val userId: String
        ) : ContentDto(ContentTypeDto.UserKicked)

        @JsonClass(generateAdapter = true)
        data class UserBanned(
            @Json(name = "id")
            val userId: String
        ) : ContentDto(ContentTypeDto.UserBanned)

        @JsonClass(generateAdapter = true)
        data class ChannelRenamed(
            val name: String,
            @Json(name = "by")
            val renamedBy: String
        ) :
            ContentDto(ContentTypeDto.ChannelRenamed)

        @JsonClass(generateAdapter = true)
        data class ChannelDescriptionChanged(
            @Json(name = "by")
            val changedBy: String) :
            ContentDto(ContentTypeDto.ChannelDescriptionChanged)

        @JsonClass(generateAdapter = true)
        data class ChannelIconChanged(
            @Json(name = "by")
            val changedBy: String) :
            ContentDto(ContentTypeDto.ChannelIconChanged)
    }

    @JsonClass(generateAdapter = true)
    data class AttachmentDto(
        @Json(name = "_id")
        val id: String,
        val tag: String,
        val size: String,
        val filename: String,
        val metadata: MetadataDto,
        @Json(name = "content_type")
        val contentType: String?
    )

    @JsonClass(generateAdapter = true)
    data class EditedDto(
        @Json(name = "\$date")
        val date: String
    )

    @JsonClass(generateAdapter = true)
    data class MasqueradeDto(
        val name: String? = null,
        val avatar: String? = null
    )
}