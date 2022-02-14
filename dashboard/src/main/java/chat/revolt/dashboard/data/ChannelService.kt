/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:34 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:34 AM
 */

package chat.revolt.dashboard.data

import chat.revolt.dashboard.data.dto.FetchMessagesRequestDto
import chat.revolt.dashboard.data.dto.FetchMessagesResponseDto
import chat.revolt.dashboard.data.dto.MessageDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChannelService {

    @GET("channels/{channelId}/messages")
    fun fetchMessages(
        @Path(value = "channelId") channelId: String,
        @Query("before") before: String?,
        @Query("limit") limit: Int,
        @Query(value = "sort") sort: String,
    ): Call<List<MessageDto>>
}