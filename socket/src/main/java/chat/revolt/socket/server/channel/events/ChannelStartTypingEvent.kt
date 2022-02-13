/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:27 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:25 PM
 */

package chat.revolt.socket.server.channel.events

import chat.revolt.socket.server.channel.ChannelEvent
import chat.revolt.socket.server.channel.type.ChannelEventType
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChannelStartTypingEvent(
    val type: ChannelEventType = ChannelEventType.ChannelStartTyping,
    val id: String,
    val user: String
) : ChannelEvent