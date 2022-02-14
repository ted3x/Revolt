/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 1:08 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 1:08 AM
 */

package chat.revolt.domain.models

data class Message(
    val id: String,
    val channel: String,
    val author: String,
    val content: String,
    val attachments: List<Attachment>?,
    val edited: String?,
    val mentions: List<String>?,
    val replies: List<String>?,
    val masquerade: Masquerade?
) {
    data class Attachment(
        val id: String?,
        val tag: String,
        val size: String?,
        val filename: String?,
        val metadata: Metadata?,
        val contentType: String?
    ) {
        data class Metadata(
            val value: String?,
            val width: Int?,
            val height: Int?
        )
    }

    data class Masquerade(
        val name: String? = null,
        val avatar: String? = null
    )
}