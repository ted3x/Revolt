/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:29 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:29 AM
 */

package chat.revolt.data.local.entity.channel

import androidx.room.Entity
import androidx.room.PrimaryKey
import chat.revolt.data.local.entity.AttachmentEntity
import chat.revolt.data.local.entity.RolePermissionsEntity
import chat.revolt.data.remote.dto.channel.ChannelType
import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.data.remote.type.EventType
import chat.revolt.domain.models.ChatItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "channel")
@JsonClass(generateAdapter = true)
data class ChannelEntity(
    @PrimaryKey
    val id: String,
    val channelType: ChannelType,
    val userId: String? = null,
    val active: Boolean? = null,
    val recipients: List<String>? = null,
    val lastMessageId: String? = null,
    val name: String? = null,
    val ownerId: String? = null,
    val description: String? = null,
    val icon: AttachmentEntity? = null,
    val permissions: Long? = null,
    val nsfw: Boolean? = null,
    val defaultPermissions: RolePermissionsEntity? = null,
    val rolePermissions: Map<String, RolePermissionsEntity>? = null,
    val server: String? = null,
)
