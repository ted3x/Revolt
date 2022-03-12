/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 8:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 8:55 PM
 */

package chat.revolt.domain.models.channel

import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User
import chat.revolt.domain.models.server.Server

sealed class Channel {
    data class SavedMessages(val id: String, val user: User) : Channel()

    data class DirectMessage(
        val id: String,
        val active: Boolean,
        val recipients: List<User>,
        val lastMessageId: String?
    ) : Channel()

    data class Group(
        val id: String,
        val recipients: List<User>,
        val name: String,
        val owner: User,
        val description: String?,
        val lastMessageId: String?,
        val icon: Message.Attachment?,
        val permissions: Int?,
        val nsfw: Boolean?
    ) : Channel()

    data class TextChannel(
        val id: String,
        val server: Server,
        val name: String,
        val description: String?,
        val icon: Message.Attachment?,
        val defaultPermissions: Int?,
        val rolePermissions: Map<String, Int>?,
        val nsfw: Boolean?,
        val lastMessageId: String?
    ): Channel()

    data class VoiceChannel(
        val id: String,
        val server: Server,
        val name: String,
        val description: String?,
        val icon: Message.Attachment?,
        val defaultPermissions: Int?,
        val rolePermissions: Map<String, Int>?,
        val nsfw: Boolean?,
    ): Channel()
}