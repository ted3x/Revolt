/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 9:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 9:00 PM
 */

package chat.revolt.domain.models.server

import chat.revolt.domain.models.Attachment
import chat.revolt.domain.models.User
import chat.revolt.domain.models.channel.Channel

data class Server(
    val id: String,
    val ownerId: String,
    val name: String,
    val description: String?,
    val channels: List<String>,
    val categories: List<Category>?,
    val systemMessages: SystemMessageChannels?,
    val roles: List<Role>?,
    val defaultPermissions: IntArray,
    val icon: Attachment?,
    val banner: Attachment?,
    val nsfw: Boolean?,
    val flags: Int?,
    val analytics: Boolean?,
    val discoverable: Boolean?
) {
    data class Category(
        val id: String,
        val title: String,
        val channels: List<String>
    )

    data class SystemMessageChannels(
        val userJoined: String?,
        val userLeft: String?,
        val userKicked: String?,
        val userBanned: String?,
    )

    data class Role(
        val id: String,
        val name: String,
        val permissions: IntArray,
        val color: String?,
        val hoist: Boolean?,
        val rank: Int
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Role

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

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + ownerId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + channels.hashCode()
        result = 31 * result + categories.hashCode()
        result = 31 * result + systemMessages.hashCode()
        result = 31 * result + roles.hashCode()
        result = 31 * result + defaultPermissions.contentHashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Server

        if (id != other.id) return false
        if (ownerId != other.ownerId) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (channels != other.channels) return false
        if (categories != other.categories) return false
        if (systemMessages != other.systemMessages) return false
        if (roles != other.roles) return false
        if (!defaultPermissions.contentEquals(other.defaultPermissions)) return false
        if (icon != other.icon) return false
        if (banner != other.banner) return false
        if (nsfw != other.nsfw) return false
        if (flags != other.flags) return false
        if (analytics != other.analytics) return false
        if (discoverable != other.discoverable) return false

        return true
    }

    companion object {
        val EMPTY = Server(
            id = "",
            ownerId = "",
            name = "",
            description = "",
            channels = listOf(),
            categories = listOf(),
            systemMessages = null,
            roles = null,
            defaultPermissions = intArrayOf(),
            icon = null,
            banner = null,
            nsfw = null,
            flags = null,
            analytics = null,
            discoverable = null
        )
    }
}
