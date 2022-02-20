/*
 * Created by Tedo Manvelidze(ted3x) on 2/20/22, 1:19 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/20/22, 1:19 AM
 */

package chat.revolt.data.local.database

import chat.revolt.data.local.entity.message.MessageEntity
import chat.revolt.domain.models.ContentType
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

internal val moshi = Moshi.Builder().build()

internal val mapAdapter: JsonAdapter<MutableMap<String, Any?>> =
    moshi.adapter(
        Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            Any::class.java
        )
    )

private val messageContentTypeAdapter =
    moshi.adapter(MessageEntity.ContentEntity.Message::class.java)
private val textTypeAdapter = moshi.adapter(MessageEntity.ContentEntity.Text::class.java)
private val userAddedTypeAdapter =
    moshi.adapter(MessageEntity.ContentEntity.UserAdded::class.java)
private val userRemovedTypeAdapter =
    moshi.adapter(MessageEntity.ContentEntity.UserRemove::class.java)
private val userJoinedAdapter =
    moshi.adapter(MessageEntity.ContentEntity.UserJoined::class.java)
private val userLeftTypeAdapter =
    moshi.adapter(MessageEntity.ContentEntity.UserLeft::class.java)
private val userKickedTypeAdapter =
    moshi.adapter(MessageEntity.ContentEntity.UserKicked::class.java)
private val userBannedTypeAdapter =
    moshi.adapter(MessageEntity.ContentEntity.UserBanned::class.java)
private val channelRenamedTypeAdapter =
    moshi.adapter(MessageEntity.ContentEntity.ChannelRenamed::class.java)
private val channelDescriptionChangedTypeAdapter =
    moshi.adapter(MessageEntity.ContentEntity.ChannelDescriptionChanged::class.java)
private val channelIconChangedTypeAdapter =
    moshi.adapter(MessageEntity.ContentEntity.ChannelIconChanged::class.java)

fun stringToMessage(string: String): MessageEntity.ContentEntity.Message {
    return messageContentTypeAdapter.fromJson(string)!!
}

fun messageToString(content: MessageEntity.ContentEntity.Message): String {
    return messageContentTypeAdapter.toJson(content).withType(content.type)
}

fun stringToText(string: String): MessageEntity.ContentEntity.Text {
    return textTypeAdapter.fromJson(string)!!
}

fun textToString(content: MessageEntity.ContentEntity.Text): String {
    return textTypeAdapter.toJson(content).withType(content.type)
}

fun stringToUserAdded(string: String): MessageEntity.ContentEntity.UserAdded {
    return userAddedTypeAdapter.fromJson(string)!!
}

fun userAddedToString(content: MessageEntity.ContentEntity.UserAdded): String {
    return userAddedTypeAdapter.toJson(content).withType(content.type)
}

fun stringToUserRemoved(string: String): MessageEntity.ContentEntity.UserRemove {
    return userRemovedTypeAdapter.fromJson(string)!!
}

fun userRemovedToString(content: MessageEntity.ContentEntity.UserRemove): String {
    return userRemovedTypeAdapter.toJson(content).withType(content.type)
}

fun stringToUserJoined(string: String): MessageEntity.ContentEntity.UserJoined {
    return userJoinedAdapter.fromJson(string)!!
}

fun userJoinedToString(content: MessageEntity.ContentEntity.UserJoined): String {
    return userJoinedAdapter.toJson(content).withType(content.type)
}

fun stringToUserLeft(string: String): MessageEntity.ContentEntity.UserLeft {
    return userLeftTypeAdapter.fromJson(string)!!
}

fun userLeftToString(content: MessageEntity.ContentEntity.UserLeft): String {
    return userLeftTypeAdapter.toJson(content).withType(content.type)
}

fun stringToUserKicked(string: String): MessageEntity.ContentEntity.UserKicked {
    return userKickedTypeAdapter.fromJson(string)!!
}

fun userKickedToString(content: MessageEntity.ContentEntity.UserKicked): String {
    return userKickedTypeAdapter.toJson(content).withType(content.type)
}

fun stringToUserBanned(string: String): MessageEntity.ContentEntity.UserBanned {
    return userBannedTypeAdapter.fromJson(string)!!
}

fun userBannedToString(content: MessageEntity.ContentEntity.UserBanned): String {
    return userBannedTypeAdapter.toJson(content).withType(content.type)
}

fun stringToChanelRenamed(string: String): MessageEntity.ContentEntity.ChannelRenamed {
    return channelRenamedTypeAdapter.fromJson(string)!!
}

fun channelRenamedToString(content: MessageEntity.ContentEntity.ChannelRenamed): String {
    return channelRenamedTypeAdapter.toJson(content).withType(content.type)
}

fun stringToChannelDescriptionChanged(string: String): MessageEntity.ContentEntity.ChannelDescriptionChanged {
    return channelDescriptionChangedTypeAdapter.fromJson(string)!!
}

fun channelDescriptionChangedToString(content: MessageEntity.ContentEntity.ChannelDescriptionChanged): String {
    return channelDescriptionChangedTypeAdapter.toJson(content).withType(content.type)
}

fun stringToChannelIconChanged(string: String): MessageEntity.ContentEntity.ChannelIconChanged {
    return channelIconChangedTypeAdapter.fromJson(string)!!
}

fun channelIconChangedToString(content: MessageEntity.ContentEntity.ChannelIconChanged): String {
    return channelIconChangedTypeAdapter.toJson(content).withType(content.type)
}
// Moshi does not include parent class field when converting class into json, so adding by hand
// Probably have to refactor with Moshi Adapter?
private fun String.withType(type: ContentType): String {
    val newMap = mapAdapter.fromJson(this)?.toMutableMap()
    newMap?.set("type", type)
    return mapAdapter.toJson(newMap)
}