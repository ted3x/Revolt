/*
 * Created by Tedo Manvelidze(ted3x) on 3/13/22, 12:02 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/13/22, 12:01 AM
 */

package chat.revolt.socket.domain.ready

import chat.revolt.domain.models.User
import chat.revolt.domain.models.channel.Channel
import chat.revolt.domain.models.server.Server

data class ReadyEvent(
    val users: List<User>,
    val servers: List<Server>,
    val channels: List<Channel>
)
