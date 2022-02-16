/*
 * Created by Tedo Manvelidze(ted3x) on 2/15/22, 4:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/15/22, 4:42 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment

import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.data.local.dao.MessageDao
import chat.revolt.data.local.mappers.MessageDBMapper

class PagingManager(
    private val messageDao: MessageDao,
    private val channelRepository: ChannelRepository,
    private val mapper: MessageDBMapper
) {
    suspend fun load(channelId: String, lastId: String? = null): PagingData {
        val newMessages = channelRepository.fetchMessages(
            channelId,
            request = FetchMessagesRequest(limit = 20, before = lastId)
        )
        if(newMessages.isNotEmpty()) {
            if(lastId == null) messageDao.clear()
            messageDao.addMessages(newMessages.map { mapper.mapToEntity(it) })
        }
        return PagingData(lastId = if(newMessages.isEmpty()) null else newMessages.last().id, newMessages.size < 20)
    }
}

data class PagingData(val lastId: String?, val isPaginationEndReached: Boolean)