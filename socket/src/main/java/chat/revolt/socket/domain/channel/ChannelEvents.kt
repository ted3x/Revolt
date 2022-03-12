/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 11:58 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 11:34 PM
 */

package chat.revolt.socket.domain.channel

import chat.revolt.domain.models.User
import chat.revolt.socket.data.channel.ChannelUpdateDto

sealed class ChannelEvents {
    data class ChannelUpdate(val channel: ChannelUpdateDto.UpdateData) : ChannelEvents()
    data class ChannelDelete(val channelId: String) : ChannelEvents()
    data class ChannelStartTyping(val channelId: String, val user: User): ChannelEvents()
    data class ChannelStopTyping(val channelId: String, val user: User): ChannelEvents()
    data class ChannelGroupJoin(val channelId: String, val user: User): ChannelEvents()
    data class ChannelGroupLeave(val channelId: String, val user: User): ChannelEvents()
    data class ChannelAck(val channelId: String, val user: User, val messageId: String): ChannelEvents()
}