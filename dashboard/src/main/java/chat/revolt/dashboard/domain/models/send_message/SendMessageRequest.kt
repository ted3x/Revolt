/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 4:19 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 4:19 PM
 */

package chat.revolt.dashboard.domain.models.send_message

import chat.revolt.domain.models.Attachment
import chat.revolt.domain.models.Message

data class SendMessageRequest(
    val channelId: String,
    val content: Message.Content.Message,
    val attachments: List<Attachment>? = null,
    val replies: List<SendMessageReply>? = null,
    val masquerade: Message.Masquerade? = null
) {
    data class SendMessageReply(val id: String, val mention: Boolean)
}
