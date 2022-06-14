/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 9:00 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 9:00 PM
 */

package chat.revolt.domain.models.server

import chat.revolt.domain.models.Attachment
import chat.revolt.domain.models.RolePermissions
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
    val defaultPermissions: Long,
    val icon: Attachment?,
    val banner: Attachment?,
    val nsfw: Boolean?,
    val flags: Flags?,
    val analytics: Boolean?,
    val discoverable: Boolean?,
    val selectedChannelId: String?
) {
    data class Category(
        val id: String,
        val title: String,
        val channels: List<String>,
        var isVisible: Boolean?
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
        val permissions: RolePermissions,
        val color: String?,
        val hoist: Boolean?,
        val rank: Int
    )

    enum class Flags {
        None,
        Official,
        Verified
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
            defaultPermissions = -1,
            icon = null,
            banner = null,
            nsfw = null,
            flags = null,
            analytics = null,
            discoverable = null,
            selectedChannelId = null
        )
    }
}
