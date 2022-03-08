/*
 * Created by Tedo Manvelidze(ted3x) on 3/8/22, 11:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/8/22, 11:48 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment

import androidx.room.withTransaction
import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.data.local.database.RevoltDatabase
import chat.revolt.data.local.mappers.MessageDBMapper
import chat.revolt.data.local.mappers.UserDBMapper
import chat.revolt.domain.repository.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MessagesManager(
    private val database: RevoltDatabase,
    private val repository: ChannelRepository,
    private val userRepository: UserRepository,
    private val usersMapper: UserDBMapper,
    private val messagesMapper: MessageDBMapper
) {

    private var lastMessageId: String? = null
    private var channelId: String = ""

    fun initChannel(channelId: String) {
        this.channelId = channelId
        GlobalScope.launch { loadMore(isInitial = true)}
    }

    suspend fun loadMore(isInitial: Boolean = false) {
        val response = repository.fetchMessages(
            request = FetchMessagesRequest(
                channelId = channelId,
                limit = 30,
                before = lastMessageId,
                sort = "Latest",
                includeUsers = true
            )
        )
        lastMessageId = response.messages.last().id
        database.withTransaction {
            if(isInitial) database.messageDao().clear(channelId)
            database.userDao().addUsers(response.users.map { usersMapper.mapToEntity(it) })
            database.messageDao().addMessages(response.messages.map { messagesMapper.mapToEntity(it) })
        }
    }

    fun getMessages() = repository.getMessages(channelId)
}