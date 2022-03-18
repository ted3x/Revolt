/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:05 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:05 AM
 */

package chat.revolt.data.remote.mappers.server

import chat.revolt.data.remote.dto.server.ServerCategoryMapper
import chat.revolt.data.remote.dto.server.ServerDto
import chat.revolt.data.remote.dto.server.ServerRolesMapper
import chat.revolt.data.remote.dto.server.SystemMessagesMapper
import chat.revolt.data.remote.mappers.message.AttachmentMapper
import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ChannelRepository
import chat.revolt.domain.repository.UserRepository

class ServerMapper(
    private val serverCategoryMapper: ServerCategoryMapper,
    private val attachmentMapper: AttachmentMapper,
    private val systemMessagesMapper: SystemMessagesMapper,
    private val serverRolesMapper: ServerRolesMapper
) {

    suspend fun mapToDomain(from: ServerDto): Server {
        return Server(
            id = from.id,
            ownerId = from.owner,
            name = from.name,
            description = from.description,
            channels = from.channels,
            categories = from.categories.map { serverCategoryMapper.mapToDomain(it) },
            systemMessages = systemMessagesMapper.mapToDomain(from.systemMessages),
            roles = from.roles.map { serverRolesMapper.mapToDomain(it.key, it.value) },
            defaultPermissions = from.defaultPermissions,
            icon = from.icon?.let { attachmentMapper.mapToDomain(it) },
            banner = from.banner?.let { attachmentMapper.mapToDomain(it) },
            nsfw = from.nsfw,
            flags = from.flags,
            analytics = from.analytics,
            discoverable = from.discoverable
        )
    }
}