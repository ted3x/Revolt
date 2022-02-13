/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:43 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:43 PM
 */

package chat.revolt.socket.server.channel.type

enum class ChannelEventType {
    ChannelCreate,
    ChannelUpdate,
    ChannelDelete,
    ChannelGroupJoin,
    ChannelGroupLeave,
    ChannelStartTyping,
    ChannelStopTyping,
    ChannelAck,
}