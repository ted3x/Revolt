/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:00 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:00 AM
 */

package chat.revolt.socket.data.ready

import chat.revolt.data.remote.dto.channel.ChannelDto
import chat.revolt.data.remote.dto.server.ServerDto
import chat.revolt.data.remote.dto.user.UserDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReadyEventDto(
    val users: List<UserDto>,
    val servers: List<ServerDto>,
    val channels: List<ChannelDto>
)
