/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 8:35 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 8:35 PM
 */

package chat.revolt.socket.server.authenticate

import chat.revolt.data.remote.type.EventType
import chat.revolt.socket.SocketAPI
import chat.revolt.socket.client.data.AuthenticateEvent
import chat.revolt.socket.data.ready.mapper.ReadyEventMapper
import chat.revolt.socket.domain.authenticate.AuthenticatedEvent
import chat.revolt.socket.domain.ready.ReadyEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

interface AuthenticateDataSource {
    fun authenticate(request: AuthenticateEvent)
    fun onAuthenticate(): Flow<AuthenticatedEvent>
    fun onReady(): Flow<ReadyEvent>
}

class AuthenticateDataSourceImpl(
    private val socket: SocketAPI,
    private val readyEventMapper: ReadyEventMapper
) : AuthenticateDataSource {
    override fun authenticate(request: AuthenticateEvent) {
        socket.authenticate(request)
    }

    override fun onAuthenticate() = socket.onAuthenticated().filter { it.type == EventType.Authenticated }.map { AuthenticatedEvent }

    override fun onReady() =
        socket.onReady().filter { it.type == EventType.Ready }.map { readyEventMapper.map(it) }

}