/*
 * Created by Tedo Manvelidze(ted3x) on 2/19/22, 12:30 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/19/22, 12:14 AM
 */

package chat.revolt.socket

import chat.revolt.data.remote.dto.channel.ChannelDto
import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.socket.client.data.AuthenticateEvent
import chat.revolt.socket.data.authenticate.AuthenticatedEventDto
import chat.revolt.socket.data.channel.ChannelActionDto
import chat.revolt.socket.data.channel.ChannelUpdateDto
import chat.revolt.socket.data.ready.ReadyEventDto
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface SocketAPI {

    @Receive
    fun observerWebSocketEvent(): Flow<WebSocket.Event>

    @Send
    fun authenticate(request: AuthenticateEvent)

    @Receive
    fun onAuthenticated(): Flow<AuthenticatedEventDto>

    @Receive
    fun onReady(): Flow<ReadyEventDto>

    @Receive
    fun onMessage(): Flow<MessageDto>

    @Receive
    fun onChannelCreate(): Flow<ChannelDto>

    @Receive
    fun onChannelUpdate(): Flow<ChannelUpdateDto>

    @Receive
    fun onChannelStartTyping(): Flow<ChannelActionDto>

    @Receive
    fun onChannelStopTyping(): Flow<ChannelActionDto>
}