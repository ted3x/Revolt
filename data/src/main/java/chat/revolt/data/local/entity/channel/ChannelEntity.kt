/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:29 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:29 AM
 */

package chat.revolt.data.local.entity.channel

import androidx.room.Entity
import androidx.room.PrimaryKey
import chat.revolt.domain.models.ChatItem

@Entity(tableName = "channel")
data class ChannelEntity(
    @PrimaryKey
    val id: String,
)
