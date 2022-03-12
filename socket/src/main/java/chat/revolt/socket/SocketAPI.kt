/*
 * Created by Tedo Manvelidze(ted3x) on 2/19/22, 12:30 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/19/22, 12:14 AM
 */

package chat.revolt.socket

import chat.revolt.socket.client.data.AuthenticateRequest
import chat.revolt.socket.server.message.*
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface SocketAPI {

    @Receive
    fun observerWebSocketEvent(): Flow<WebSocket.Event>

    @Send
    fun authenticate(request: AuthenticateRequest)

    @Receive
    fun onAuthenticated(): Flow<AuthenticatedEvent>

    @Receive
    fun onReady(): Flow<ReadyEvent>

    @Receive
    fun onMessage(): Flow<MessageEvent>

    @Receive
    fun onChannelStartTyping(): Flow<ChannelStartTypingEvent>

    @Receive
    fun onChannelStopTyping(): Flow<ChannelStopTypingEvent>
}