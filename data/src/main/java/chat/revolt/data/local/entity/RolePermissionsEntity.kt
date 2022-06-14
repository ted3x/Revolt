/*
 * Created by Tedo Manvelidze(ted3x) on 6/15/22, 12:17 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 6/15/22, 12:17 AM
 */

package chat.revolt.data.local.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RolePermissionsEntity(
    val allowed: Long,
    val disallowed: Long
)