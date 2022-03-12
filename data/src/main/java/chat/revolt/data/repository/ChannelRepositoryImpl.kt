/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:12 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:12 AM
 */

package chat.revolt.data.repository

import chat.revolt.data.local.dao.ChannelDao
import chat.revolt.data.local.mappers.channel.ChannelEntityMapper
import chat.revolt.data.remote.data_source.channel.ChannelDataSource
import chat.revolt.data.remote.mappers.channel.ChannelMapper
import chat.revolt.domain.models.channel.Channel
import chat.revolt.domain.repository.ChannelRepository
import java.lang.IllegalStateException

class ChannelRepositoryImpl(
    private val channelDao: ChannelDao,
    private val channelDataSource: ChannelDataSource,
    private val channelMapper: ChannelMapper,
    private val channelEntityMapper: ChannelEntityMapper
) : ChannelRepository {
    override suspend fun getChannel(channelId: String): Channel {
        val channel = channelDao.getChannel(channelId)
        return channel?.let { channelEntityMapper.mapToDomain(it) } ?: fetchChannel(channelId)
        ?: throw IllegalStateException(
            "Channel with $channelId not found"
        )
    }

    override suspend fun fetchChannel(channelId: String): Channel? {
        val channel = channelDataSource.fetchChannel(channelId)
            ?.let { channelMapper.mapToDomain(it) }
        return channel?.also { channelDao.addChannel(channelEntityMapper.mapToEntity(it)) }
    }
}