/*
 * Created by Tedo Manvelidze(ted3x) on 3/5/22, 9:05 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/5/22, 9:05 PM
 */

package chat.revolt.data.local.entity.channel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channel_remote_key")
data class ChannelRemoteKey(
    @PrimaryKey
    val channel: String,
    val nextPageKey: String?
)