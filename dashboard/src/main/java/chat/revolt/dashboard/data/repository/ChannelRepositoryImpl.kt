/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:55 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:55 AM
 */

package chat.revolt.dashboard.data.repository

import chat.revolt.dashboard.data.data_source.ChannelDataSource
import chat.revolt.dashboard.data.mapper.FetchMessageMapper
import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.data.local.dao.MessageDao
import chat.revolt.data.local.mappers.MessageDBMapper
import chat.revolt.domain.models.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChannelRepositoryImpl(
    private val messageDao: MessageDao,
    private val dataSource: ChannelDataSource,
    private val mapper: FetchMessageMapper
) : ChannelRepository {

    override fun getMessages(channelId: String): Flow<List<Message>> {
        return messageDao.getMessages()
            .map { it.map { MessageDBMapper().mapToDomain(it) } }
    }

    override suspend fun fetchMessages(
        channelId: String,
        request: FetchMessagesRequest
    ): List<Message> {
        return mapper.mapToResponse(
            dataSource.fetchMessages(
                channelId,
                mapper.mapToRequest(request)
            )
        )
    }
}