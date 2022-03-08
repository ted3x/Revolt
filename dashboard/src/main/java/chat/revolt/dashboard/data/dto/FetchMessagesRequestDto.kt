/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:36 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:36 AM
 */

package chat.revolt.dashboard.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FetchMessagesRequestDto(
    val channelId: String,
    val limit: Int,
    val before: String? = null,
    val after: String? = null,
    val sort: String,
    val nearby: String? = null,
    @Json(name = "include_users")
    val includeUsers: Boolean
)