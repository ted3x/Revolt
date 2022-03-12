/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 4:19 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 4:19 PM
 */

package chat.revolt.dashboard.data.dto.send_message

import chat.revolt.data.remote.dto.AttachmentDto
import chat.revolt.data.remote.dto.message.MessageDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendMessageRequestDto(
    val channelId: String,
    val content: String,
    val attachments: List<AttachmentDto>?,
    val replies: List<SendMessageReplyDto>?,
    val masquerade: MessageDto.MasqueradeDto?
) {
    @JsonClass(generateAdapter = true)
    data class SendMessageReplyDto(val id: String, val mention: Boolean)
}
