/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 5:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 5:55 PM
 */

package chat.revolt.domain.repository

import chat.revolt.domain.models.server.Server

interface ServerRepository {

    suspend fun getServer(serverId: String): Server
}