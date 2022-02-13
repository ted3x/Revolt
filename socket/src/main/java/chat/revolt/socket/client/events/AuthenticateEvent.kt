/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:51 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:51 PM
 */

package chat.revolt.socket.client.events

import chat.revolt.socket.client.ClientEvent
import chat.revolt.socket.client.type.ClientEventType
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticateEvent(
    val type: ClientEventType = ClientEventType.Authenticate,
    val token: String
) : ClientEvent