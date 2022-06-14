/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:59 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:59 PM
 */

package chat.revolt.data.local.entity.server

import androidx.room.Entity
import androidx.room.PrimaryKey
import chat.revolt.data.local.entity.AttachmentEntity
import chat.revolt.data.local.entity.RolePermissionsEntity
import com.squareup.moshi.JsonClass

@Entity(tableName = "server")
@JsonClass(generateAdapter = true)
data class ServerEntity(
    @PrimaryKey
    val id: String,
    val ownerId: String,
    val name: String,
    val description: String?,
    val channels: List<String>,
    val categories: List<Category>?,
    val systemMessages: SystemMessageChannels?,
    val roles: Map<String, Role>?,
    val defaultPermissions: Long,
    val icon: AttachmentEntity?,
    val banner: AttachmentEntity?,
    val nsfw: Boolean?,
    val flags: Int?,
    val analytics: Boolean?,
    val discoverable: Boolean?,
    val selectedChannelId: String?
) {

    @JsonClass(generateAdapter = true)
    data class Category(
        val id: String,
        val title: String,
        val channels: List<String>,
        val isVisible: Boolean
    )

    @JsonClass(generateAdapter = true)
    data class SystemMessageChannels(
        val userJoined: String?,
        val userLeft: String?,
        val userKicked: String?,
        val userBanned: String?,
    )

    @JsonClass(generateAdapter = true)
    data class Role(
        val name: String,
        val permissions: RolePermissionsEntity,
        val color: String?,
        val hoist: Boolean?,
        val rank: Int
    )
}