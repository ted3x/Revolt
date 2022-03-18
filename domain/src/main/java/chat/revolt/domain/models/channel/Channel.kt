/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 8:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 8:55 PM
 */

package chat.revolt.domain.models.channel

import chat.revolt.domain.models.Attachment
import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User
import chat.revolt.domain.models.server.Server

sealed class Channel(open val id: String) {
    data class SavedMessages(override val id: String, val userId: String) : Channel(id)

    data class DirectMessage(
        override val id: String,
        val active: Boolean,
        val recipients: List<String>,
        val lastMessageId: String?
    ) : Channel(id)

    data class Group(
        override val id: String,
        val recipients: List<String>,
        val name: String,
        val ownerId: String,
        val description: String?,
        val lastMessageId: String?,
        val icon: Attachment?,
        val permissions: Int?,
        val nsfw: Boolean?
    ) : Channel(id)

    data class TextChannel(
        override val id: String,
        val serverId: String,
        val name: String,
        val description: String?,
        val icon: Attachment?,
        val defaultPermissions: Int?,
        val rolePermissions: Map<String, Int>?,
        val nsfw: Boolean?,
        val lastMessageId: String?
    ): Channel(id)

    data class VoiceChannel(
        override val id: String,
        val serverId: String,
        val name: String,
        val description: String?,
        val icon: Attachment?,
        val defaultPermissions: Int?,
        val rolePermissions: Map<String, Int>?,
        val nsfw: Boolean?,
    ): Channel(id)
}