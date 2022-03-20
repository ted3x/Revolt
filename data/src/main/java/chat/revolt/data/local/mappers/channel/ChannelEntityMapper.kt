/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:16 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:16 AM
 */

package chat.revolt.data.local.mappers.channel

import chat.revolt.data.local.entity.channel.ChannelEntity
import chat.revolt.data.local.mappers.AttachmentEntityMapper
import chat.revolt.data.local.mappers.EntityDomainMapper
import chat.revolt.data.remote.dto.channel.ChannelType
import chat.revolt.domain.models.channel.Channel

class ChannelEntityMapper(private val attachmentMapper: AttachmentEntityMapper) :
    EntityDomainMapper<ChannelEntity, Channel> {

    override fun mapToDomain(from: ChannelEntity): Channel {
        return when (from.channelType) {
            ChannelType.SavedMessages -> Channel.SavedMessages(
                id = from.id,
                userId = from.userId!!
            )
            ChannelType.DirectMessage -> Channel.DirectMessage(
                id = from.id,
                name = from.name!!,
                active = from.active!!,
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

    override fun mapToEntity(from: Channel): ChannelEntity {
        return when (from) {
            is Channel.SavedMessages -> ChannelEntity(
                id = from.id,
                userId = from.userId,
                channelType = ChannelType.SavedMessages
            )
            is Channel.DirectMessage -> ChannelEntity(
                id = from.id,
                active = from.active,
                recipients = from.recipients,
                lastMessageId = from.lastMessageId,
                channelType = ChannelType.DirectMessage
            )
            is Channel.Group -> ChannelEntity(
                id = from.id,
                recipients = from.recipients,
                name = from.name,
                ownerId = from.ownerId,
                description = from.description,
                lastMessageId = from.lastMessageId,
                icon = from.icon?.let { attachmentMapper.mapToEntity(it) },
                permissions = from.permissions,
                nsfw = from.nsfw,
                channelType = ChannelType.Group
            )
            is Channel.TextChannel -> ChannelEntity(
                id = from.id,
                server = from.serverId,
                name = from.name,
                description = from.description,
                icon = from.icon?.let { attachmentMapper.mapToEntity(it) },
                defaultPermissions = from.defaultPermissions,
                rolePermissions = from.rolePermissions,
                nsfw = from.nsfw,
                lastMessageId = from.lastMessageId,
                channelType = ChannelType.TextChannel
            )
            is Channel.VoiceChannel -> ChannelEntity(
                id = from.id,
                server = from.serverId,
                name = from.name,
                description = from.description,
                icon = from.icon?.let { attachmentMapper.mapToEntity(it) },
                defaultPermissions = from.defaultPermissions,
                rolePermissions = from.rolePermissions,
                nsfw = from.nsfw,
                channelType = ChannelType.VoiceChannel
            )
        }
    }
}