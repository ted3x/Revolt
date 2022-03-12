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

class ChannelMapper(
    private val userRepository: UserRepository,
    private val attachmentMapper: AttachmentMapper,
    private val serverRepository: ServerRepository
) {

    suspend fun mapToDomain(from: ChannelDto): Channel {
        return when (from.channelType) {
            ChannelType.SavedMessages -> Channel.SavedMessages(
                id = from.id,
                user = userRepository.getUser(from.userId!!)
            )
            ChannelType.DirectMessage -> Channel.DirectMessage(
                id = from.id,
                active = from.active!!,
                recipients = userRepository.getUsers(from.recipients!!),
                lastMessageId = from.lastMessageId
            )
            ChannelType.Group -> Channel.Group(
                id = from.id,
                recipients = userRepository.getUsers(from.recipients!!),
                name = from.name!!,
                owner = userRepository.getUser(from.ownerId!!),
                description = from.description,
                lastMessageId = from.lastMessageId,
                icon = from.icon?.let { attachmentMapper.mapToDomain(it) },
                permissions = from.permissions,
                nsfw = from.nsfw
            )
            ChannelType.TextChannel -> Channel.TextChannel(
                id = from.id,
                server = serverRepository.getServer(from.server!!),
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
                server = serverRepository.getServer(from.server!!),
                name = from.name!!,
                description = from.description,
                icon = from.icon?.let { attachmentMapper.mapToDomain(it) },
                defaultPermissions = from.defaultPermissions,
                rolePermissions = from.rolePermissions,
                nsfw = from.nsfw,
            )
        }
    }

    suspend fun mapToDto(from: Channel): ChannelDto {
        return when (from) {
            is Channel.SavedMessages -> ChannelDto(
                id = from.id,
                userId = from.user.id,
                channelType = ChannelType.SavedMessages
            )
            is Channel.DirectMessage -> ChannelDto(
                id = from.id,
                active = from.active,
                recipients = from.recipients.map { it.id },
                lastMessageId = from.lastMessageId,
                channelType = ChannelType.DirectMessage
            )
            is Channel.Group -> ChannelDto(
                id = from.id,
                recipients = from.recipients.map { it.id },
                name = from.name,
                ownerId = from.owner.id,
                description = from.description,
                lastMessageId = from.lastMessageId,
                icon = from.icon?.let { attachmentMapper.mapToDto(it) },
                permissions = from.permissions,
                nsfw = from.nsfw,
                channelType = ChannelType.Group
            )
            is Channel.TextChannel -> ChannelDto(
                id = from.id,
                server = from.server.id,
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
                server = from.server.id,
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