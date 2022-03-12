/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 11:59 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 11:59 PM
 */

package chat.revolt.socket.data.authenticate

import chat.revolt.data.remote.type.EventType
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticatedEventDto(val type: EventType)
