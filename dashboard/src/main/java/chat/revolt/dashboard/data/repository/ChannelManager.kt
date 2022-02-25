/*
 * Created by Tedo Manvelidze(ted3x) on 2/20/22, 9:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/20/22, 9:42 PM
 */

package chat.revolt.dashboard.data.repository

import androidx.room.withTransaction
import chat.revolt.core.UlidTimeDecoder
import chat.revolt.core.extensions.forEach
import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.models.FetchMessagesResponse
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.data.local.database.RevoltDatabase
import chat.revolt.domain.models.Message
import chat.revolt.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.last
import java.util.*

class ChannelManager(
    private val database: RevoltDatabase,
    private val channelRepository: ChannelRepository,
    private val userRepository: UserRepository
) {

    private lateinit var channelId: String
    val messages: MutableStateFlow<List<Message>> = MutableStateFlow(emptyList())
    private var isPaginationEndReached = false
    private var lastId: String? = null
    private var startDate: Long = System.currentTimeMillis()
    private var counter = 0

    suspend fun initializeMessages(channelId: String, limit: Int = MESSAGES_LIMIT) {
        this.channelId = channelId
        val messages = channelRepository.getMessagesBefore(channelId, startDate, limit)
        if( messages.isNotEmpty()) lastId = messages.last().id
        counter += messages.size
        this.messages.emit(messages)
        loadFreshMessages()
    }

    private suspend fun loadFreshMessages() {
        val response = channelRepository.fetchMessages(
            channelId,
            request = FetchMessagesRequest(limit = MESSAGES_LIMIT)
        )

        val newMessages = response.messages
        if (newMessages.isNotEmpty()) {
            database.withTransaction {
                userRepository.addUsers(response.users)
                channelRepository.addMessages(newMessages)
                this.lastId = newMessages.first().id
                startDate = UlidTimeDecoder.getTimestamp(this.lastId.toString())
                val latestMessages = channelRepository.getMessagesBefore(
                    channelId,
                    startDate,
                    MESSAGES_LIMIT
                )
                messages.emit(latestMessages)
                counter += latestMessages.size
                isPaginationEndReached = newMessages.size < MESSAGES_LIMIT
            }
        }
    }

    suspend fun loadMoreMessages() {
        if (isPaginationEndReached) return
        val response = channelRepository.fetchMessages(
            channelId,
            request = FetchMessagesRequest(limit = MESSAGES_LIMIT, before = lastId)
        )

        val newMessages = response.messages
        if (newMessages.isNotEmpty()) {
            database.withTransaction {
                userRepository.addUsers(response.users)
                channelRepository.addMessages(newMessages)
                val latestMessages = channelRepository.getMessagesBefore(
                    channelId,
                    startDate,
                    MESSAGES_LIMIT
                )
                this.lastId = latestMessages.last().id
                startDate = UlidTimeDecoder.getTimestamp(this.lastId.toString())
                messages.emit(messages.value + latestMessages)
                counter += latestMessages.size
                isPaginationEndReached = newMessages.size < MESSAGES_LIMIT
            }
        }
    }

    fun isPaginationEndReached() = isPaginationEndReached

    companion object {
        private const val MESSAGES_LIMIT = 20
    }
}