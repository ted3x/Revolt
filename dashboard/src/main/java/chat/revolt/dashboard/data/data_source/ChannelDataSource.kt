/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:45 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:45 AM
 */

package chat.revolt.dashboard.data.data_source

import chat.revolt.dashboard.data.dto.fetch_messages.FetchMessagesRequestDto
import chat.revolt.dashboard.data.dto.fetch_messages.FetchMessagesResponseDto
import chat.revolt.dashboard.data.dto.send_message.SendMessageRequestDto
import chat.revolt.data.remote.dto.message.MessageDto

interface ChannelDataSource {

    suspend fun fetchMessages(request: FetchMessagesRequestDto): FetchMessagesResponseDto
    suspend fun sendMessage(request: SendMessageRequestDto): MessageDto
}