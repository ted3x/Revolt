/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:12 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:12 AM
 */

package chat.revolt.data.remote.service.channel

import chat.revolt.data.remote.dto.channel.ChannelDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChannelService {

    @GET("channels/{channelId}")
    fun fetchChannel(@Path("channelId") channelId: String): Call<ChannelDto>
}