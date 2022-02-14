/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:59 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:59 AM
 */

package chat.revolt.data.local.entity.message

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val id: String,
    val channel: String,
    val author: String,
    val content: String,
    val edited: String?,
    val createdAt: Long,
    val mentions: List<String>?,
    val replies: List<String>?,
)