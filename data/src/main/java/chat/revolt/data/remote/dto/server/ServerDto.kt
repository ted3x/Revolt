/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 8:39 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 8:39 PM
 */

package chat.revolt.data.remote.dto.server

import chat.revolt.data.remote.dto.AttachmentDto
import chat.revolt.data.remote.dto.RolePermissionsDto
import chat.revolt.data.remote.dto.message.MessageDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerDto(
    @Json(name = "_id")
    val id: String,
    val owner: String,
    val name: String,
    val description: String?,
    val channels: List<String>,
    val categories: List<CategoryDto>,
    @Json(name = "system_messages")
    val systemMessages: SystemMessageChannelsDto,
    val roles: Map<String, RoleDto>,
    @Json(name = "default_permissions")
    val defaultPermissions: Long,
    val icon: AttachmentDto?,
    val banner: AttachmentDto?,
    val nsfw: Boolean?,
    val flags: Int?,
    val analytics: Boolean?,
    val discoverable: Boolean?
) {

    @JsonClass(generateAdapter = true)
    data class CategoryDto(
        val id: String,
        val title: String,
        val channels: List<String>
    )

    @JsonClass(generateAdapter = true)
    data class SystemMessageChannelsDto(
        @Json(name = "user_joined")
        val userJoined: String?,
        @Json(name = "user_left")
        val userLeft: String?,
        @Json(name = "user_kicked")
        val userKicked: String?,
        @Json(name = "user_banned")
        val userBanned: String?,
    )

    @JsonClass(generateAdapter = true)
    data class RoleDto(
        val name: String,
        val permissions: RolePermissionsDto,
        @Json(name = "colour")
        val color: String?,
        val hoist: Boolean?,
        val rank: Int
    )
}
