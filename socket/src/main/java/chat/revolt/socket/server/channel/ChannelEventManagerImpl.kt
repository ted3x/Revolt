/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:26 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:26 PM
 */

package chat.revolt.socket.server.channel

import chat.revolt.socket.server.channel.events.ChannelStartTypingEvent
import chat.revolt.socket.server.channel.events.ChannelStopTypingEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter

class ChannelEventManagerImpl(
    private val channelStreams: Flow<ChannelEvent?>
) :
    ChannelEventManager {

    override suspend fun onChannelCreate() {
        TODO("Not yet implemented")
    }

    override suspend fun onChannelDelete() {
        TODO("Not yet implemented")
    }

    override suspend fun onChannelGroupJoin() {
        TODO("Not yet implemented")
    }

    override suspend fun onChannelGroupLeave() {
        TODO("Not yet implemented")
    }

    override suspend fun onChannelStartTyping(): Flow<ChannelStartTypingEvent> {
        return channelStreams.filter { it is ChannelStartTypingEvent } as Flow<ChannelStartTypingEvent>
    }

    override suspend fun onChannelStopTyping(): Flow<ChannelStopTypingEvent> {
        return channelStreams.filter { it is ChannelStopTypingEvent } as Flow<ChannelStopTypingEvent>
    }

    override suspend fun onChannelAck() {
        TODO("Not yet implemented")
    }

}