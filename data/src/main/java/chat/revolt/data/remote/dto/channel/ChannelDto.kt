/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 8:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 8:48 PM
 */

package chat.revolt.data.remote.dto.channel

import chat.revolt.data.remote.dto.message.MessageDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChannelDto(
    @Json(name = "_id")
    val id: String,
    @Json(name = "channel_type")
    val channelType: ChannelType,
    @Json(name = "user")
    val userId: String?,
    val active: Boolean?,
    val recipients: List<String>?,
    @Json(name = "last_message_id")
    val lastMessageId: String?,
    val name: String?,
    @Json(name = "owner")
    val ownerId: String?,
    val description: String?,
    val icon: MessageDto.AttachmentDto?,
    val permissions: Int?,
    val nsfw: Boolean?,
    @Json(name = "default_permissions")
    val defaultPermissions: Int?,
    @Json(name = "role_permissions")
    val rolePermissions: Map<String, Int>?
)