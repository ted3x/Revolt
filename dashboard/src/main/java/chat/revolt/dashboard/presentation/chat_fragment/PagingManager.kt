/*
 * Created by Tedo Manvelidze(ted3x) on 2/15/22, 4:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/15/22, 4:42 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment

import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.data.local.dao.MessageDao
import chat.revolt.data.local.dao.UserDao
import chat.revolt.data.local.mappers.MessageDBMapper
import chat.revolt.data.local.mappers.UserDBMapper

class PagingManager(
    private val messageDao: MessageDao,
    private val channelRepository: ChannelRepository,
    private val mapper: MessageDBMapper,
    private val userDao: UserDao,
    private val userDBMapper: UserDBMapper
) {
    suspend fun load(channelId: String, lastId: String? = null): PagingData {
        val response = channelRepository.fetchMessages(
            channelId,
            request = FetchMessagesRequest(limit = 20, before = lastId)
        )
        val newMessages = response.messages
        if (newMessages.isNotEmpty()) {
            if (lastId == null) messageDao.clear()
            userDao.addUsers(response.users.map { userDBMapper.mapToEntity(it) })
            messageDao.addMessages(newMessages.map { mapper.mapToEntity(it) })
        }
        return PagingData(
            lastId = if (newMessages.isEmpty()) null else newMessages.last().id,
            newMessages.size < 20
        )
    }
}

data class PagingData(val lastId: String?, val isPaginationEndReached: Boolean)