/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 11:37 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 11:37 PM
 */

package chat.revolt.data.remote.mappers.channel

import chat.revolt.data.remote.dto.channel.ChannelDto
import chat.revolt.data.remote.dto.channel.ChannelType
import chat.revolt.data.remote.mappers.message.AttachmentMapper
import chat.revolt.domain.models.channel.Channel
import chat.revolt.domain.repository.ServerRepository
import chat.revolt.domain.repository.UserRepository

class ChannelMapper(private val attachmentMapper: AttachmentMapper, private val userRepository: UserRepository) {

    suspend fun mapToDomain(from: ChannelDto): Channel {
        return when (from.channelType) {
            ChannelType.SavedMessages -> Channel.SavedMessages(
                id = from.id,
                userId = from.userId!!
            )
            ChannelType.DirectMessage -> Channel.DirectMessage(
                id = from.id,
                active = from.active!!,
                name = userRepository.getUser(from.recipients!!.last()).username,
                recipients = from.recipients!!,
                lastMessageId = from.lastMessageId
            )
            ChannelType.Group -> Channel.Group(
                id = from.id,
                recipients = from.recipients!!,
                name = from.name!!,
                ownerId = from.ownerId!!,
                description = from.description,
                lastMessageId = from.lastMessageId,
                icon = from.icon?.let { attachmentMapper.mapToDomain(it) },
                permissions = from.permissions,
                nsfw = from.nsfw
            )
            ChannelType.TextChannel -> Channel.TextChannel(
                id = from.id,
                serverId = from.server!!,
                name = from.name!!,
                description = from.description,
                icon = from.icon?.let { attachmentMapper.mapToDomain(it) },
                defaultPermissions = from.defaultPermissions,
                rolePermissions = from.rolePermissions,
                nsfw = from.nsfw,
                lastMessageId = from.lastMessageId
            )
            ChannelType.VoiceChannel -> Channel.VoiceChannel(
                id = from.id,
                serverId = from.server!!,
                name = from.name!!,
                description = from.description,
                icon = from.icon?.let { attachmentMapper.mapToDomain(it) },
                defaultPermissions = from.defaultPermissions,
                rolePermissions = from.rolePermissions,
                nsfw = from.nsfw,
            )
        }
    }

    fun mapToDto(from: Channel): ChannelDto {
        return when (from) {
            is Channel.SavedMessages -> ChannelDto(
                id = from.id,
                userId = from.userId,
                channelType = ChannelType.SavedMessages
            )
            is Channel.DirectMessage -> ChannelDto(
                id = from.id,
                active = from.active,
                recipients = from.recipients,
                lastMessageId = from.lastMessageId,
                channelType = ChannelType.DirectMessage
            )
            is Channel.Group -> ChannelDto(
                id = from.id,
                recipients = from.recipients,
                name = from.name,
                ownerId = from.ownerId,
                description = from.description,
                lastMessageId = from.lastMessageId,
                icon = from.icon?.let { attachmentMapper.mapToDto(it) },
                permissions = from.permissions,
                nsfw = from.nsfw,
                channelType = ChannelType.Group
            )
            is Channel.TextChannel -> ChannelDto(
                id = from.id,
                server = from.serverId,
                name = from.name,
                description = from.description,
                icon = from.icon?.let { attachmentMapper.mapToDto(it) },
                defaultPermissions = from.defaultPermissions,
                rolePermissions = from.rolePermissions,
                nsfw = from.nsfw,
                lastMessageId = from.lastMessageId,
                channelType = ChannelType.TextChannel
            )
            is Channel.VoiceChannel -> ChannelDto(
                id = from.id,
                server = from.serverId,
                name = from.name,
                description = from.description,
                icon = from.icon?.let { attachmentMapper.mapToDto(it) },
                defaultPermissions = from.defaultPermissions,
                rolePermissions = from.rolePermissions,
                nsfw = from.nsfw,
                channelType = ChannelType.VoiceChannel
            )
        }
    }
}