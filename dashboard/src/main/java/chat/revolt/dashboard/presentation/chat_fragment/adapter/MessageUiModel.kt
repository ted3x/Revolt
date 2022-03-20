/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 11:06 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 11:06 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.adapter

import chat.revolt.domain.UlidTimeDecoder
import chat.revolt.domain.models.Attachment
import chat.revolt.domain.models.Message
import kotlin.math.abs

data class MessageUiModel(
    val id: String,
    val authorId: String,
    val authorName: String,
    val authorAvatarUrl: String,
    val content: String,
    val attachments: List<Attachment>?,
    val edited: String?,
    val mentions: List<String>?,
    val replies: List<String>?,
) {

    val timestamp = UlidTimeDecoder.getTimestamp(id)

    fun isDivided(previousMessage: MessageUiModel): Boolean {
        return (previousMessage.authorId != authorId || (previousMessage.authorId == authorId && abs(
            previousMessage.timestamp - timestamp
        ) > DIVIDER_MAX_TIME))
    }

    companion object {
        private const val DIVIDER_MAX_TIME = 420000

        val EMPTY =
            MessageUiModel(
                id = "",
                authorId = "",
                authorName = "",
                authorAvatarUrl = "",
                content = "",
                attachments = null,
                edited = null,
                mentions = null,
                replies = null,
            )
    }
}