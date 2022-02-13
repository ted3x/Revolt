/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:20 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:20 PM
 */

package chat.revolt.socket.server

import chat.revolt.Event
import chat.revolt.socket.api.RevoltSocket
import chat.revolt.socket.event_resolver.EventResolver
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

fun <E: Event, T> RevoltSocket.allEventsStream(eventResolver: EventResolver<E, T>): Flow<E?> {
    return this.observerWebSocketEvent()
        .filter { it is WebSocket.Event.OnMessageReceived }
        .map { (it as WebSocket.Event.OnMessageReceived).message }
        .filter { it is Message.Text }
        .map { (it as Message.Text).value }
        .map(eventResolver::resolveEventFromJson)
}