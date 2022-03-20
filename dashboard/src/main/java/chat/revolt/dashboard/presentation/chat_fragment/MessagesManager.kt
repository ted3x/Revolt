/*
 * Created by Tedo Manvelidze(ted3x) on 3/8/22, 11:48 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/8/22, 11:48 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment

import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import chat.revolt.core.extensions.Error
import chat.revolt.core.extensions.ResultWrapper.Companion.onError
import chat.revolt.core.extensions.ResultWrapper.Companion.onSuccess
import chat.revolt.core.network.NetworkStateManager
import chat.revolt.dashboard.domain.models.fetch_messages.FetchMessagesRequest
import chat.revolt.dashboard.domain.models.send_message.SendMessageRequest
import chat.revolt.dashboard.domain.repository.MessagesRepository
import chat.revolt.data.local.database.RevoltDatabase
import chat.revolt.domain.models.Message
import chat.revolt.domain.repository.UserRepository
import chat.revolt.domain.repository.member.MemberRepository

class MessagesManager(
    private val database: RevoltDatabase,
    private val messagesRepository: MessagesRepository,
    private val userRepository: UserRepository,
    private val memberRepository: MemberRepository,
    private val networkStateManager: NetworkStateManager
) {

    val isEndReached = MutableLiveData(false)
    private var lastMessageId: String? = null
    private var channelId: String = ""

    fun initChannel(channelId: String) {
        isEndReached.value = false
        lastMessageId = null
        this.channelId = channelId
    }

    suspend fun loadMore(isInitial: Boolean = false) {
        if (isEndReached.value == true) return
       messagesRepository.fetchMessages(
            request = FetchMessagesRequest(
                channelId = channelId,
                limit = LIMIT,
                before = lastMessageId,
                sort = "Latest",
                includeUsers = true
            )
        ).onSuccess {
           lastMessageId = if (it.messages.size < LIMIT) {
               isEndReached.postValue(true)
               null
           } else it.messages.last().id

           database.withTransaction {
               if (isInitial) messagesRepository.clear(channelId)
               userRepository.addUsers(it.users)
               messagesRepository.addMessages(it.messages)
               it.members?.let { members -> memberRepository.addMembers(members) }
           }
       }.onError { _, error ->
           when(error) {
               Error.NetworkError -> {}
               Error.NoConnection, Error.Timeout -> if(networkStateManager.isConnected()) networkStateManager.noInternet()
           }
       }
    }

    fun getMessages() = messagesRepository.getMessages(channelId)
    suspend fun getInitialMessages() = messagesRepository.getInitialMessages(channelId, LIMIT)

    suspend fun sendMessage(message: String) {
        messagesRepository.sendMessage(request = SendMessageRequest(
            channelId = channelId,
            content = Message.Content.Message(message),
        )).also { messagesRepository.addMessage(it) }
    }
    companion object {
        private const val LIMIT = 30
    }
}