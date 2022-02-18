/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:46 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:46 AM
 */

package chat.revolt.dashboard.domain.repository

import chat.revolt.dashboard.domain.models.FetchMessagesRequest
import chat.revolt.dashboard.domain.models.FetchMessagesResponse
import chat.revolt.domain.models.Message
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

    fun getMessages(channelId: String): Flow<List<Message>>
    suspend fun addMessage(message: Message)
    suspend fun fetchMessages(channelId: String, request: FetchMessagesRequest): FetchMessagesResponse
}