/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:43 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:20 PM
 */

package chat.revolt.socket.server.type

enum class ServerEventType {
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