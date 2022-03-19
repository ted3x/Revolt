/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 3:30 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 3:30 PM
 */

package chat.revolt.data.remote.mappers.server

import chat.revolt.domain.models.server.Server

class ServerFlagsMapper {
    fun map(from: Int): Server.Flags {
        return when(from) {
            0 -> Server.Flags.None
            1 -> Server.Flags.Official
            2 -> Server.Flags.Verified
            else -> Server.Flags.None
        }
    }
}