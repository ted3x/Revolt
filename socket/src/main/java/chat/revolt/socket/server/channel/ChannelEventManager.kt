/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:25 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:25 PM
 */

package chat.revolt.socket.server.channel

import chat.revolt.socket.server.channel.events.ChannelStartTypingEvent
import chat.revolt.socket.server.channel.events.ChannelStopTypingEvent
import kotlinx.coroutines.flow.Flow

interface ChannelEventManager {

    suspend fun onChannelCreate()
    suspend fun onChannelDelete()
    suspend fun onChannelGroupJoin()
    suspend fun onChannelGroupLeave()
    suspend fun onChannelStartTyping(): Flow<ChannelStartTypingEvent>
    suspend fun onChannelStopTyping(): Flow<ChannelStopTypingEvent>
    suspend fun onChannelAck()
}