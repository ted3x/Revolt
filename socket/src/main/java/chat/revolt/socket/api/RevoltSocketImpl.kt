/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:20 PM
 */

package chat.revolt.socket.api

import android.util.Log
import chat.revolt.core.server_config.RevoltConfig
import chat.revolt.core.server_config.RevoltConfigManager
import chat.revolt.socket.client.events.AuthenticateEvent
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.collect

class RevoltSocketImpl(private val instance: RevoltSocket, ) {

    private val token = "qUL5nTUE0PR6XOI7V2URjhA8A25y0FVmMYfmdeNwFH8OZG4W6ftJJAiYAAAS8PR1"
    suspend fun start() {
        instance.observerWebSocketEvent().collect {
            when (it) {
                is WebSocket.Event.OnConnectionOpened<*> -> {
                    Log.d("WebSocket", "OnConnectionOpened")
                    instance.authenticate(AuthenticateEvent(token = token))
                }
                is WebSocket.Event.OnMessageReceived -> {
                    Log.d("WebSocket", "MessageReceived")
                }
                is WebSocket.Event.OnConnectionClosing -> {
                    Log.d("WebSocket", "OnClosing")
                }
                is WebSocket.Event.OnConnectionClosed -> {
                    Log.d("WebSocket", "OnConnectionClosed")
                }
                is WebSocket.Event.OnConnectionFailed -> {
                    Log.d("WebSocket", "OnConnectionFailed")
                }
            }
        }
    }
}