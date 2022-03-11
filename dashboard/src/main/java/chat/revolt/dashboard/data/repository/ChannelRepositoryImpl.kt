/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:55 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:55 AM
 */

package chat.revolt.dashboard.data.repository

import chat.revolt.dashboard.data.data_source.ChannelDataSource
import chat.revolt.dashboard.data.mapper.FetchMessageMapper
import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.models.FetchMessagesResponse
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.data.local.dao.MessageDao
import chat.revolt.data.local.mappers.MessageDBMapper
import chat.revolt.data.local.mappers.UserDBMapper
import chat.revolt.domain.models.Message
import chat.revolt.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChannelRepositoryImpl(
    private val messageDao: MessageDao,
    private val userRepository: UserRepository,
    private val dataSource: ChannelDataSource,
    private val mapper: FetchMessageMapper,
    private val messageMapper: MessageDBMapper,
    private val userDBMapper: UserDBMapper
) : ChannelRepository {

    override fun getMessages(channelId: String): Flow<List<Message>> {
        return messageDao.getMessages(channelId).map { it.map { messageMapper.mapToDomain(it) } }
    }

    override suspend fun addMessage(message: Message) {
        messageDao.addMessage(messageMapper.mapToEntity(message))
    }

    override suspend fun addMessages(messages: List<Message>) {
        messageDao.addMessages(messages.map { messageMapper.mapToEntity(it) })
    }

    override suspend fun fetchMessages(
        request: FetchMessagesRequest
    ): FetchMessagesResponse {
        return mapper.mapToResponse(dataSource.fetchMessages(mapper.mapToRequest(request)))
    }

    override suspend fun deleteMessage(messageId: String) {
        messageDao.deleteMessage(messageId)
    }

    override suspend fun clear(channelId: String) {
        messageDao.clear(channelId)
    }
}