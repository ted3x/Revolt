/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 11:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 11:51 PM
 */

package chat.revolt.data.repository

import chat.revolt.data.local.dao.ServerDao
import chat.revolt.data.local.mappers.server.ServerCategoryEntityMapper
import chat.revolt.data.local.mappers.server.ServerEntityMapper
import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ServerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest

class ServerRepositoryImpl(
    private val serverDao: ServerDao,
    private val serverEntityMapper: ServerEntityMapper,
    private val serverCategoryMapper: ServerCategoryEntityMapper
) : ServerRepository {
    override suspend fun getServer(serverId: String): Server? {
        return serverDao.getServer(serverId)?.let { serverEntityMapper.mapToDomain(it) }
    }

    override fun getServerAsFlow(serverId: String): Flow<Server> {
        return serverDao.getServerAsFlow(serverId).mapLatest { serverEntityMapper.mapToDomain(it) }
    }

    override suspend fun addServers(servers: List<Server>) {
        serverDao.addServers(servers.map {
            val serverEntity = serverDao.getServer(it.id)
            val server = serverEntityMapper.mapToEntity(it)
            val categories = server.categories?.map { category ->
                category.copy(
                    isVisible = serverEntity?.categories?.first { it.id == category.id }?.isVisible
                        ?: true
                )
            }
            server.copy(
                categories = categories,
                selectedChannelId = serverEntity?.selectedChannelId
            )
        })
    }

    override fun getServers(): Flow<List<Server>> {
        return serverDao.getServers().mapLatest { serverEntityMapper.mapToDomain(it) }
    }

    override suspend fun updateCategory(serverId: String, categories: List<Server.Category>) {
        serverDao.updateCategory(serverId, serverCategoryMapper.mapToEntity(categories))
    }

    override suspend fun updateSelectedChannel(serverId: String, channelId: String) {
        serverDao.updateSelectedChannel(serverId, channelId)
    }
}