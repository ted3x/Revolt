/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 10:49 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 10:49 PM
 */

package chat.revolt.data.local.entity.member

import androidx.room.Entity
import chat.revolt.data.local.entity.AttachmentEntity

@Entity(tableName = "members", primaryKeys = ["userId", "serverId"])
data class MemberEntity(
    val userId: String,
    val serverId: String,
    val nickname: String?,
    val avatar: AttachmentEntity?,
    val roles: List<String>?
)
