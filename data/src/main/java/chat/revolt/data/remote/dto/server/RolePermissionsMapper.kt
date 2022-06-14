/*
 * Created by Tedo Manvelidze(ted3x) on 6/15/22, 12:04 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 6/15/22, 12:04 AM
 */

package chat.revolt.data.remote.dto.server

import chat.revolt.data.remote.dto.RolePermissionsDto
import chat.revolt.domain.models.RolePermissions

class RolePermissionsMapper {
    fun mapToDomain(from: RolePermissionsDto): RolePermissions {
        return RolePermissions(allowed = from.allowed, disallowed = from.disallowed)
    }

    fun mapToDto(from: RolePermissions): RolePermissionsDto {
        return RolePermissionsDto(allowed = from.allowed, disallowed = from.disallowed)
    }
}