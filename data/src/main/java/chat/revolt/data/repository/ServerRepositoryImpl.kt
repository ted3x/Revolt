/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 11:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 11:51 PM
 */

package chat.revolt.data.repository

import chat.revolt.domain.models.server.Server
import chat.revolt.domain.repository.ServerRepository

class ServerRepositoryImpl: ServerRepository {
    override suspend fun getServer(serverId: String): Server {
        return Server.EMPTY
    }
}