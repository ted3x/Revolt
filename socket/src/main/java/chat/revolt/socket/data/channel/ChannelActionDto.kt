/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 10:15 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 10:15 PM
 */

package chat.revolt.socket.data.channel

import chat.revolt.data.remote.type.EventType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChannelActionDto(
    val type: EventType,
    val id: String,
    @Json(name = "user")
    val userId: String?,
    @Json(name = "message_Id")
    val messageId: String?
)