/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:14 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:14 AM
 */

package chat.revolt.data.remote.data_source.channel

import chat.revolt.data.remote.dto.channel.ChannelDto

interface ChannelDataSource {

    suspend fun fetchChannel(channelId: String): ChannelDto?
}