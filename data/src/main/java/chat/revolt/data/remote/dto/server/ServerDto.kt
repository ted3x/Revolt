/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 8:39 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 8:39 PM
 */

package chat.revolt.data.remote.dto.server

import chat.revolt.data.remote.dto.AttachmentDto
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
    val defaultPermissions: IntArray,
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
        val permissions: IntArray,
        @Json(name = "colour")
        val color: String?,
        val hoist: Boolean?,
        val rank: Int
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as RoleDto

            if (name != other.name) return false
            if (!permissions.contentEquals(other.permissions)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + permissions.contentHashCode()
            return result
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerDto

        if (id != other.id) return false
        if (owner != other.owner) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (channels != other.channels) return false
        if (categories != other.categories) return false
        if (systemMessages != other.systemMessages) return false
        if (roles != other.roles) return false
        if (!defaultPermissions.contentEquals(other.defaultPermissions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + owner.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + channels.hashCode()
        result = 31 * result + categories.hashCode()
        result = 31 * result + systemMessages.hashCode()
        result = 31 * result + roles.hashCode()
        result = 31 * result + defaultPermissions.contentHashCode()
        return result
    }
}
