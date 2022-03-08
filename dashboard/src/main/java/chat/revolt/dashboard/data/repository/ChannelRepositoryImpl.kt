/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:55 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:55 AM
 */

package chat.revolt.dashboard.data.repository

import androidx.paging.*
import chat.revolt.dashboard.data.data_source.ChannelDataSource
import chat.revolt.dashboard.data.mapper.FetchMessageMapper
import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.models.FetchMessagesResponse
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.dashboard.presentation.new_chat_fragment.NewMessagesMediator
import chat.revolt.data.local.dao.MessageDao
import chat.revolt.data.local.dao.UserDao
import chat.revolt.data.local.database.RevoltDatabase
import chat.revolt.data.local.entity.message.MessageEntity
import chat.revolt.data.local.mappers.MessageDBMapper
import chat.revolt.data.local.mappers.UserDBMapper
import chat.revolt.domain.models.Message
import chat.revolt.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import org.koin.java.KoinJavaComponent
import java.lang.IllegalStateException

class ChannelRepositoryImpl(
    private val userDao: UserDao,
    private val messageDao: MessageDao,
    private val dataSource: ChannelDataSource,
    private val mapper: FetchMessageMapper,
    private val messageMapper: MessageDBMapper,
    private val userDBMapper: UserDBMapper
) : ChannelRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getMessages(channelId: String): Flow<PagingData<Message>> {
        val userMapper = UserDBMapper()
        val userRepository = KoinJavaComponent.get<UserRepository>(UserRepository::class.java)
        val database = KoinJavaComponent.get<RevoltDatabase>(RevoltDatabase::class.java)
        val mediator = NewMessagesMediator(
            userMapper,
            MessageDBMapper(userMapper, userRepository),
            channelId,
            database = database,
            repository = this
        )
        val pager = Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            remoteMediator = mediator
        ){
            messageDao.getMessages(channelId)
        }
        return pager.flow.mapLatest { it.map { messageMapper.mapToDomain(it)} }
    }

    override suspend fun addMessage(message: Message) {
        messageDao.addMessage(messageMapper.mapToEntity(message))
    }

    override suspend fun addMessages(messages: List<Message>) {
        messageDao.addMessages(messages.map { messageMapper.mapToEntity(it) })
    }

    override suspend fun fetchMessages(
        channelId: String,
        request: FetchMessagesRequest
    ): FetchMessagesResponse {
        return mapper.mapToResponse(
            dataSource.fetchMessages(
                channelId,
                mapper.mapToRequest(request)
            ),
            userDao.getCurrentUser()?.let { userDBMapper.mapToDomain(it) }
                ?: throw IllegalStateException("User not found")
        )
    }

    override suspend fun deleteMessage(messageId: String) {
        messageDao.deleteMessage(messageId)
    }
}