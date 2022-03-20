/*
 * Created by Tedo Manvelidze(ted3x) on 3/18/22, 11:59 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/18/22, 11:59 PM
 */

package chat.revolt.data.local.entity.server

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import chat.revolt.data.local.entity.AttachmentEntity
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
    val defaultPermissions: IntArray,
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
        val permissions: IntArray,
        val color: String?,
        val hoist: Boolean?,
        val rank: Int
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Role

            if (name != other.name) return false
            if (!permissions.contentEquals(other.permissions)) return false
            if (color != other.color) return false
            if (hoist != other.hoist) return false
            if (rank != other.rank) return false

            return true
        }

        override fun hashCode(): Int {
            var result = name.hashCode()
            result = 31 * result + permissions.contentHashCode()
            result = 31 * result + (color?.hashCode() ?: 0)
            result = 31 * result + (hoist?.hashCode() ?: 0)
            result = 31 * result + rank
            return result
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerEntity

        if (id != other.id) return false
        if (ownerId != other.ownerId) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (channels != other.channels) return false
        if (categories != other.categories) return false
        if (systemMessages != other.systemMessages) return false
        if (roles != other.roles) return false
        if (!defaultPermissions.contentEquals(other.defaultPermissions)) return false
        if (icon != other.icon) return false
        if (banner != other.banner) return false
        if (nsfw != other.nsfw) return false
        if (flags != other.flags) return false
        if (analytics != other.analytics) return false
        if (discoverable != other.discoverable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + ownerId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + channels.hashCode()
        result = 31 * result + categories.hashCode()
        result = 31 * result + systemMessages.hashCode()
        result = 31 * result + roles.hashCode()
        result = 31 * result + defaultPermissions.contentHashCode()
        result = 31 * result + (icon?.hashCode() ?: 0)
        result = 31 * result + (banner?.hashCode() ?: 0)
        result = 31 * result + (nsfw?.hashCode() ?: 0)
        result = 31 * result + (flags ?: 0)
        result = 31 * result + (analytics?.hashCode() ?: 0)
        result = 31 * result + (discoverable?.hashCode() ?: 0)
        return result
    }
}