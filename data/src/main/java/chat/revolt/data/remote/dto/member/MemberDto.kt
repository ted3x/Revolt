/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 10:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 10:42 PM
 */

package chat.revolt.data.remote.dto.member

import chat.revolt.data.remote.dto.AttachmentDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MemberDto(
    @Json(name = "_id")
    val id: Identifier,
    val nickname: String?,
    val avatar: AttachmentDto?,
    val roles: List<String>?
) {
    @JsonClass(generateAdapter = true)
    data class Identifier(
        @Json(name = "server")
        val serverId: String,
        @Json(name = "user")
        val userId: String
    )
}
