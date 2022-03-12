/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:34 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:34 AM
 */

package chat.revolt.dashboard.data

import chat.revolt.dashboard.data.dto.fetch_messages.FetchMessagesResponseDto
import chat.revolt.dashboard.data.dto.send_message.SendMessageRequestDto
import chat.revolt.data.remote.dto.message.MessageDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChannelService {

    @GET("channels/{channelId}/messages")
    fun fetchMessages(
        @Path(value = "channelId") channelId: String,
        @Query("before") before: String?,
        @Query("limit") limit: Int,
        @Query(value = "sort") sort: String,
        @Query(value = "include_users") includeUsers: Boolean
    ): Call<FetchMessagesResponseDto>

    @POST("channels/{channelId}/messages")
    fun sendMessage(
        @Path(value = "channelId") channelId: String,
        @Query("content") content: MessageDto.ContentDto,
        @Query("attachments") attachments: List<MessageDto.AttachmentDto>,
        @Query("replies") replies: List<SendMessageRequestDto.SendMessageReplyDto>,
        @Query("masquerade") masquerade: MessageDto.MasqueradeDto
    ): Call<MessageDto>
}