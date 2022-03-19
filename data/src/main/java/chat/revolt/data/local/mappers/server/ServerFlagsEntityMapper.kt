/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 3:33 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 3:33 PM
 */

package chat.revolt.data.local.mappers.server

import chat.revolt.data.local.mappers.EntityDomainMapper
import chat.revolt.domain.models.server.Server

class ServerFlagsEntityMapper : EntityDomainMapper<Int, Server.Flags> {

    override fun mapToDomain(from: Int): Server.Flags {
        return when (from) {
            0 -> Server.Flags.None
            1 -> Server.Flags.Official
            2 -> Server.Flags.Verified
            else -> Server.Flags.None
        }
    }

    override fun mapToEntity(from: Server.Flags): Int {
        return when (from) {
            Server.Flags.None -> 0
            Server.Flags.Official -> 1
            Server.Flags.Verified -> 2
            else -> 0
        }
    }
}