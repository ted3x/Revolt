/*
 * Created by Tedo Manvelidze(ted3x) on 2/17/22, 9:56 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/17/22, 9:56 PM
 */

package chat.revolt.data.remote.dto.message

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageDto(
    @Json(name = "_id")
    val id: String,
    val nonce: String? = null,
    val channel: String,
    val author: String,
    val content: String,
    val attachments: List<AttachmentDto>?,
    val edited: EditedDto?,
    val embeds: List<Any>?,
    val mentions: List<String>?,
    val replies: List<String>?,
    val masquerade: MasqueradeDto?
) {

    @JsonClass(generateAdapter = true)
    data class AttachmentDto(
        @Json(name = "_id")
        val id: String?,
        val tag: String,
        val size: String?,
        val filename: String?,
        val metadata: MetadataDto?,
        @Json(name = "content_type")
        val contentType: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class MetadataDto(
            val value: String?,
            val width: Int?,
            val height: Int?
        )
    }

    @JsonClass(generateAdapter = true)
    data class EditedDto(
        @Json(name = "\$date")
        val date: String
    )

    @JsonClass(generateAdapter = true)
    data class MasqueradeDto(
        val name: String? = null,
        val avatar: String? = null
    )
}