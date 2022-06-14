/*
 * Created by Tedo Manvelidze(ted3x) on 6/15/22, 12:17 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 6/15/22, 12:17 AM
 */

package chat.revolt.domain.models

data class RolePermissions(
    val allowed: Long,
    val disallowed: Long
)