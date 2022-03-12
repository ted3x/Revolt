/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:46 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:46 AM
 */

package chat.revolt.dashboard.domain.repository

import chat.revolt.dashboard.domain.models.fetch_messages.FetchMessagesRequest
import chat.revolt.dashboard.domain.models.fetch_messages.FetchMessagesResponse
import chat.revolt.dashboard.domain.models.send_message.SendMessageRequest
import chat.revolt.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface MessagesRepository {

    fun getMessages(channelId: String): Flow<List<Message>>
    suspend fun getInitialMessages(channelId: String, limit: Int): List<Message>
    suspend fun addMessage(message: Message)
    suspend fun addMessages(messages: List<Message>)
    suspend fun sendMessage(request: SendMessageRequest): Message
    suspend fun fetchMessages(request: FetchMessagesRequest): FetchMessagesResponse
    suspend fun deleteMessage(messageId: String)
    suspend fun clear(channelId: String)
}