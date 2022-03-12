/*
 * Created by Tedo Manvelidze(ted3x) on 3/8/22, 11:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/8/22, 11:48 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment

import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import chat.revolt.dashboard.domain.models.fetch_messages.FetchMessagesRequest
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.data.local.database.RevoltDatabase
import chat.revolt.domain.repository.UserRepository

class MessagesManager(
    private val database: RevoltDatabase,
    private val channelRepository: ChannelRepository,
    private val userRepository: UserRepository,
) {

    val isEndReached = MutableLiveData(false)
    private var lastMessageId: String? = null
    private var channelId: String = ""

    fun initChannel(channelId: String) {
        isEndReached.value = false
        this.channelId = channelId
    }

    suspend fun loadMore(isInitial: Boolean = false) {
        if (isEndReached.value == true) return
        val response = channelRepository.fetchMessages(
            request = FetchMessagesRequest(
                channelId = channelId,
                limit = LIMIT,
                before = lastMessageId,
                sort = "Latest",
                includeUsers = true
            )
        )
        lastMessageId = if (response.messages.size < LIMIT) {
            isEndReached.postValue(true)
            null
        } else response.messages.last().id

        database.withTransaction {
            if (isInitial) channelRepository.clear(channelId)
            userRepository.addUsers(response.users)
            channelRepository.addMessages(response.messages)
        }
    }

    fun getMessages() = channelRepository.getMessages(channelId)
    suspend fun getInitialMessages() = channelRepository.getInitialMessages(channelId, LIMIT)

    companion object {
        private const val LIMIT = 30
    }
}