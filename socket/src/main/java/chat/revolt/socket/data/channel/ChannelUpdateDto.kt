/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 11:58 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 10:49 PM
 */

package chat.revolt.socket.data.channel

import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.data.remote.type.EventType
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChannelUpdateDto(
    val type: EventType,
    val id: String,
    val data: UpdateData,
    val clear: String?
) {
    @JsonClass(generateAdapter = true)
    data class UpdateData(
        val name: String?,
        val description: String?,
        val nsfw: Boolean?,
        val icon: MessageDto.AttachmentDto?,
        @Json(name = "role_permissions")
        val rolePermissions: Map<String, Int>?
    )
}
