/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:20 PM
 */

package chat.revolt.socket.api

import chat.revolt.socket.SocketAPI
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.collect

class ClientSocketManager(private val socket: SocketAPI) {

    suspend fun initialize(listener: RevoltSocketListener) {
        socket.observerWebSocketEvent().collect {
            when (it) {
                is WebSocket.Event.OnConnectionOpened<*> -> listener.onConnectionOpened()
                is WebSocket.Event.OnMessageReceived -> listener.onMessageReceived(it.message.toString())
                is WebSocket.Event.OnConnectionClosing -> listener.onConnectionClosing()
                is WebSocket.Event.OnConnectionClosed -> listener.onConnectionClosed()
                is WebSocket.Event.OnConnectionFailed -> listener.onConnectionFailed()
            }
        }
    }
}