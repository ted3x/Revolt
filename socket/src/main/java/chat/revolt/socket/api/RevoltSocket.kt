/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:18 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 3:41 PM
 */

package chat.revolt.socket.api

import chat.revolt.socket.client.events.AuthenticateEvent
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface RevoltSocket {

    @Send
    fun authenticate(event: AuthenticateEvent)

    @Receive
    fun observerWebSocketEvent(): Flow<WebSocket.Event>
}