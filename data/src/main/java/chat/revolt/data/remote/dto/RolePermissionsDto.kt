/*
 * Created by Tedo Manvelidze(ted3x) on 6/15/22, 12:14 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 6/15/22, 12:14 AM
 */

package chat.revolt.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RolePermissionsDto(
    @Json(name = "a")
    val allowed: Long,
    @Json(name = "d")
    val disallowed: Long
)