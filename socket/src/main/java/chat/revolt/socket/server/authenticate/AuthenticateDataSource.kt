/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 8:35 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 8:35 PM
 */

package chat.revolt.socket.server.authenticate

import chat.revolt.socket.SocketAPI
import chat.revolt.socket.client.data.AuthenticateRequest
import chat.revolt.socket.server.message.AuthenticatedEvent
import chat.revolt.socket.server.message.ReadyEvent
import kotlinx.coroutines.flow.Flow

interface AuthenticateDataSource {
    fun authenticate(request: AuthenticateRequest)
    fun onAuthenticate(): Flow<AuthenticatedEvent>
    fun onReady(): Flow<ReadyEvent>
}

class AuthenticateDataSourceImpl(private val socket: SocketAPI): AuthenticateDataSource {
    override fun authenticate(request: AuthenticateRequest) {
        socket.authenticate(request)
    }

    override fun onAuthenticate() = socket.onAuthenticated()

    override fun onReady() = socket.onReady()

}