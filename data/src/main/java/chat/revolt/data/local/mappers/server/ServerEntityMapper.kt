/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 12:03 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 12:03 AM
 */

package chat.revolt.data.local.mappers.server

import chat.revolt.data.local.entity.server.ServerEntity
import chat.revolt.data.local.mappers.AttachmentEntityMapper
import chat.revolt.data.local.mappers.EntityDomainMapper
import chat.revolt.data.remote.mappers.server.ServerFlagsMapper
import chat.revolt.domain.models.server.Server

class ServerEntityMapper(
    private val categoriesMapper: ServerCategoryEntityMapper,
    private val systemMessagesMapper: SystemMessagesEntityMapper,
    private val serverRolesMapper: ServerRolesEntityMapper,
    private val attachmentEntityMapper: AttachmentEntityMapper,
    private val serverFlagsMapper: ServerFlagsEntityMapper
) : EntityDomainMapper<ServerEntity, Server> {
    override fun mapToDomain(from: ServerEntity): Server {
        return Server(
            id = from.id,
            ownerId = from.ownerId,
            name = from.name,
            description = from.description,
            channels = from.channels,
            categories = from.categories?.let { categoriesMapper.mapToDomain(it) },
            systemMessages = from.systemMessages?.let { systemMessagesMapper.mapToDomain(it) },
            roles = from.roles?.map { serverRolesMapper.mapToDomain(it.key, it.value) },
            defaultPermissions = from.defaultPermissions,
            icon = from.icon?.let { attachmentEntityMapper.mapToDomain(it) },
            banner = from.banner?.let { attachmentEntityMapper.mapToDomain(it) },
            nsfw = from.nsfw,
            flags = from.flags?.let { serverFlagsMapper.mapToDomain(it) },
            analytics = from.analytics,
            discoverable = from.discoverable,
            selectedChannelId = from.selectedChannelId
        )
    }

    override fun mapToEntity(from: Server): ServerEntity {
        return ServerEntity(
            id = from.id,
            ownerId = from.ownerId,
            name = from.name,
            description = from.description,
            channels = from.channels,
            categories = from.categories?.map { categoriesMapper.mapToEntity(it) },
            systemMessages = from.systemMessages?.let { systemMessagesMapper.mapToEntity(it) },
            roles = from.roles?.let { serverRolesMapper.mapToEntity(it) },
            defaultPermissions = from.defaultPermissions,
            icon = from.icon?.let { attachmentEntityMapper.mapToEntity(it) },
            banner = from.banner?.let { attachmentEntityMapper.mapToEntity(it) },
            nsfw = from.nsfw,
            flags = from.flags?.let { serverFlagsMapper.mapToEntity(it) },
            analytics = from.analytics,
            discoverable = from.discoverable,
            selectedChannelId = from.selectedChannelId
        )
    }
}