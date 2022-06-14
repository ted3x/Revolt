/*
 * Created by Tedo Manvelidze(ted3x) on 2/11/22, 2:08 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/11/22, 2:08 AM
 */

package chat.revolt.data.local.database

import androidx.room.TypeConverter
import chat.revolt.data.local.entity.AttachmentEntity
import chat.revolt.data.local.entity.RolePermissionsEntity
import chat.revolt.data.local.entity.message.MessageEntity
import chat.revolt.data.local.entity.server.ServerEntity
import chat.revolt.data.local.entity.user.UserEntity
import chat.revolt.domain.models.ContentType
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import java.lang.IllegalStateException
import java.lang.NullPointerException

class DatabaseConverters {

    private val relationshipType =
        Types.newParameterizedType(List::class.java, UserEntity.Relationship::class.java)
    private val relationshipAdapter = moshi.adapter<List<UserEntity.Relationship>>(relationshipType)

    private val statusAdapter = moshi.adapter(UserEntity.Status::class.java)

    private val botAdapter = moshi.adapter(UserEntity.Bot::class.java)

    private val avatarAdapter = moshi.adapter(UserEntity.Avatar::class.java)

    private val attachmentAdapter = moshi.adapter(AttachmentEntity::class.java)

    private val attachmentType =
        Types.newParameterizedType(List::class.java, AttachmentEntity::class.java)
    private val attachmentListAdapter = moshi.adapter<List<AttachmentEntity>>(attachmentType)

    private val mapStringToIntAdapter: JsonAdapter<Map<String, Int>> =
        moshi.adapter(
            Types.newParameterizedType(
                Map::class.java,
                String::class.java,
                Integer::class.java
            )
        )

    private val serverCategoryType =
        Types.newParameterizedType(List::class.java, ServerEntity.Category::class.java)
    private val serverCategoriesAdapter = moshi.adapter<List<ServerEntity.Category>>(serverCategoryType)
    private val systemMessagesAdapter =
        moshi.adapter(ServerEntity.SystemMessageChannels::class.java)
    private val rolesType = Types.newParameterizedType(
        Map::class.java,
        String::class.java,
        ServerEntity.Role::class.java
    )
    private val rolesAdapter = moshi.adapter<Map<String, ServerEntity.Role>>(rolesType)
    private val rolePermissionsType = Types.newParameterizedType(
        Map::class.java,
        String::class.java,
        RolePermissionsEntity::class.java
    )
    private val rolePermissionsAdapter = moshi.adapter<Map<String, RolePermissionsEntity>>(rolePermissionsType)
    private val defaultRolePermissionsAdapter = moshi.adapter(RolePermissionsEntity::class.java)

    @TypeConverter
    fun stringToRelationships(string: String): List<UserEntity.Relationship> {
        return relationshipAdapter.fromJson(string).orEmpty()
    }

    @TypeConverter
    fun relationshipsToString(members: List<UserEntity.Relationship>?): String {
        return relationshipAdapter.toJson(members)
    }

    @TypeConverter
    fun stringToStatus(string: String): UserEntity.Status {
        return statusAdapter.fromJson(string)
            ?: throw IllegalStateException("Wrong $string as UserEntity.Status")
    }

    @TypeConverter
    fun statusToString(status: UserEntity.Status): String {
        return statusAdapter.toJson(status)
    }

    @TypeConverter
    fun stringToBot(string: String): UserEntity.Bot? {
        return botAdapter.fromJson(string)
    }

    @TypeConverter
    fun botToString(bot: UserEntity.Bot?): String {
        return botAdapter.toJson(bot)
    }

    @TypeConverter
    fun stringToAvatar(string: String): UserEntity.Avatar? {
        return avatarAdapter.fromJson(string)
    }

    @TypeConverter
    fun avatarToString(avatar: UserEntity.Avatar?): String {
        return avatarAdapter.toJson(avatar)
    }

    @TypeConverter
    fun stringToAttachment(string: String): AttachmentEntity? {
        return attachmentAdapter.fromJson(string)
    }

    @TypeConverter
    fun attachmentToString(attachment: AttachmentEntity?): String {
        return attachmentAdapter.toJson(attachment)
    }

    @TypeConverter
    fun stringToAttachmentList(string: String): List<AttachmentEntity>? {
        return attachmentListAdapter.fromJson(string)
    }

    @TypeConverter
    fun attachmentListToString(attachment: List<AttachmentEntity>?): String {
        return attachmentListAdapter.toJson(attachment)
    }

    @TypeConverter
    fun stringToList(string: String): List<String>? {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        return moshi.adapter<List<String>>(type).fromJson(string)
    }

    @TypeConverter
    fun listToString(list: List<String>?): String {
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        return moshi.adapter<List<String>>(type).toJson(list)
    }

    @TypeConverter
    fun stringToMapStringToInt(string: String): Map<String, Int>? {
        return mapStringToIntAdapter.fromJson(string)
    }

    @TypeConverter
    fun mapStringToIntToString(map: Map<String, Int>?): String {
        return mapStringToIntAdapter.toJson(map)
    }

    @TypeConverter
    fun stringToServerCategory(string: String): List<ServerEntity.Category> {
        return serverCategoriesAdapter.fromJson(string)!!
    }

    @TypeConverter
    fun serverCategoryToString(serverCategory: List<ServerEntity.Category>): String {
        return serverCategoriesAdapter.toJson(serverCategory)
    }

    @TypeConverter
    fun stringToSystemMessages(string: String): ServerEntity.SystemMessageChannels {
        return systemMessagesAdapter.fromJson(string)!!
    }

    @TypeConverter
    fun systemMessagesToString(systemMessages: ServerEntity.SystemMessageChannels): String {
        return systemMessagesAdapter.toJson(systemMessages)
    }

    @TypeConverter
    fun stringToRoles(string: String): Map<String, ServerEntity.Role> {
        return rolesAdapter.fromJson(string)!!
    }

    @TypeConverter
    fun rolesToString(roles: Map<String, ServerEntity.Role>): String {
        return rolesAdapter.toJson(roles)
    }

    @TypeConverter
    fun stringToRolePermissions(string: String): Map<String, RolePermissionsEntity>? {
        return rolePermissionsAdapter.fromJson(string)
    }

    @TypeConverter
    fun rolePermissionsToString(permissions: Map<String, RolePermissionsEntity>?): String {
        return rolePermissionsAdapter.toJson(permissions)
    }

    @TypeConverter
    fun stringToDefaultRolePermissions(string: String): RolePermissionsEntity? {
        return defaultRolePermissionsAdapter.fromJson(string)
    }

    @TypeConverter
    fun defaultRolePermissionsToString(permissions: RolePermissionsEntity?): String {
        return defaultRolePermissionsAdapter.toJson(permissions)
    }

    @TypeConverter
    fun contentToString(content: MessageEntity.ContentEntity): String {
        return when (content) {
            is MessageEntity.ContentEntity.ChannelDescriptionChanged -> channelDescriptionChangedToString(
                content
            )
            is MessageEntity.ContentEntity.ChannelIconChanged -> channelIconChangedToString(content)
            is MessageEntity.ContentEntity.ChannelRenamed -> channelRenamedToString(content)
            is MessageEntity.ContentEntity.Message -> messageToString(content)
            is MessageEntity.ContentEntity.Text -> textToString(content)
            is MessageEntity.ContentEntity.UserAdded -> userAddedToString(content)
            is MessageEntity.ContentEntity.UserBanned -> userBannedToString(content)
            is MessageEntity.ContentEntity.UserJoined -> userJoinedToString(content)
            is MessageEntity.ContentEntity.UserKicked -> userKickedToString(content)
            is MessageEntity.ContentEntity.UserLeft -> userLeftToString(content)
            is MessageEntity.ContentEntity.UserRemove -> userRemovedToString(content)
        }
    }

    @TypeConverter
    fun stringToContent(string: String): MessageEntity.ContentEntity {
        val mapValues = mapAdapter.fromJson(string)
        val type =
            mapValues?.get("type") as? String ?: throw NullPointerException("type can't be null")
        return when (ContentType.valueOf(type)) {
            ContentType.Text -> stringToText(string)
            ContentType.UserAdded -> stringToUserAdded(string)
            ContentType.UserRemove -> stringToUserRemoved(string)
            ContentType.UserJoined -> stringToUserJoined(string)
            ContentType.UserLeft -> stringToUserLeft(string)
            ContentType.UserKicked -> stringToUserKicked(string)
            ContentType.UserBanned -> stringToUserBanned(string)
            ContentType.ChannelRenamed -> stringToChanelRenamed(string)
            ContentType.ChannelDescriptionChanged -> stringToChannelDescriptionChanged(string)
            ContentType.ChannelIconChanged -> stringToChannelIconChanged(string)
            ContentType.Message -> stringToMessage(string)
            else -> throw IllegalStateException("wrong $type passed")
        }
    }
}