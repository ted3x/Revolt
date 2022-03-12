/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 8:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 8:48 PM
 */

package chat.revolt.data.remote.dto.channel

import chat.revolt.data.remote.dto.AttachmentDto
import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.data.remote.type.EventType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChannelDto(
    @Json(name = "_id")
    val id: String,
    @Json(name = "channel_type")
    val channelType: ChannelType,
    @Json(name = "user")
    val userId: String? = null,
    val active: Boolean? = null,
    val recipients: List<String>? = null,
    @Json(name = "last_message_id")
    val lastMessageId: String? = null,
    val name: String? = null,
    @Json(name = "owner")
    val ownerId: String? = null,
    val description: String? = null,
    val icon: AttachmentDto? = null,
    val permissions: Int? = null,
    val nsfw: Boolean? = null,
    @Json(name = "default_permissions")
    val defaultPermissions: Int? = null,
    @Json(name = "role_permissions")
    val rolePermissions: Map<String, Int>? = null,
    val server: String? = null,
    @Json(name = "type")
    val eventType: EventType? = null
)