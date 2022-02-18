/*
 * Created by Tedo Manvelidze(ted3x) on 2/18/22, 1:20 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/18/22, 1:20 AM
 */

package chat.revolt.data.remote.type

import com.squareup.moshi.Json

enum class EventType {
    // ========= Client
    @Json(name = "Authenticate")
    Authenticate,
    BeginTyping,
    EndTyping,
    Ping,

    // ======== Server
    //Generic
    Error,
    Authenticated,
    Pong,
    Ready,

    //Message
    Message,
    MessageUpdate,
    MessageDelete,

    //Channel

    //Server
    ServerUpdate,
    ServerDelete,
    ServerMemberUpdate,
    ServerMemberJoin,
    ServerMemberLeave,
    ServerRoleUpdate,
    ServerRoleDelete,

    //User
    UserUpdate,
    UserRelationship
}