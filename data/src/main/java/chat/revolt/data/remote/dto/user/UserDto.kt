/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 8:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 8:42 PM
 */

package chat.revolt.data.remote.dto.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "_id")
    val id: String,
    val username: String,
    val avatar: AvatarDto?,
    val relations: List<RelationshipDto>?,
    val badges: Int?,
    val status: StatusDto?,
    val relationship: String?,
    val online: Boolean?,
    val flags: Int?,
    val bot: BotDto?
) {

    @JsonClass(generateAdapter = true)
    data class AvatarDto(
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
    data class RelationshipDto(
        val status: String,
        @Json(name = "_id")
        val id: String
    )

    @JsonClass(generateAdapter = true)
    data class StatusDto(
        val text: String?,
        val presence: String?
    )

    @JsonClass(generateAdapter = true)
    data class BotDto(
        val owner: String
    )
}