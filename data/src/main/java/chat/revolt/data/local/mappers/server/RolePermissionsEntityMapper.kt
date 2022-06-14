/*
 * Created by Tedo Manvelidze(ted3x) on 6/15/22, 12:04 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 6/15/22, 12:04 AM
 */

package chat.revolt.data.local.mappers.server

import chat.revolt.data.local.entity.RolePermissionsEntity
import chat.revolt.domain.models.RolePermissions

class RolePermissionsEntityMapper {
    fun mapToDomain(from: RolePermissionsEntity): RolePermissions {
        return RolePermissions(allowed = from.allowed, disallowed = from.disallowed)
    }

    fun mapToEntity(from: RolePermissions): RolePermissionsEntity {
        return RolePermissionsEntity(allowed = from.allowed, disallowed = from.disallowed)
    }
}