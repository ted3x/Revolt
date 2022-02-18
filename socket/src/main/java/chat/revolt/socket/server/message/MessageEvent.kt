/*
 * Created by Tedo Manvelidze(ted3x) on 2/18/22, 11:47 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/18/22, 10:37 PM
 */

package chat.revolt.socket.server.message

import chat.revolt.socket.events.Event
import chat.revolt.socket.events.Events
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageEvent(
    @Json(name = "_id")
    val id: String,
    val nonce: String? = null,
    val channel: String,
    val author: String,
    val content: String,
    val attachments: List<Attachment>?,
    val embeds: List<Any>?,
    val mentions: List<String>?,
    val masquerade: Masquerade?
): Events() {

    override val type: Event = Event.Message

    @JsonClass(generateAdapter = true)
    data class Attachment(
        @Json(name = "_id")
        val id: String?,
        val tag: String,
        val size: String?,
        val filename: String?,
        val metadata: Metadata?,
        @Json(name = "content_type")
        val contentType: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Metadata(
            val value: String?,
            val width: Int?,
            val height: Int?
        )
    }

    @JsonClass(generateAdapter = true)
    data class Masquerade(
        val name: String? = null,
        val avatar: String? = null
    )
}
