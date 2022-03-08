/*
 * Created by Tedo Manvelidze(ted3x) on 3/9/22, 1:26 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/9/22, 1:26 AM
 */

package chat.revolt.socket.server.message

import chat.revolt.data.remote.dto.message.MessageDto
import com.squareup.moshi.*
import java.lang.IllegalStateException
import java.lang.reflect.Type

class MessageEventContentAdapter : JsonAdapter.Factory {
    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? =
        when (type) {
            MessageEvent.Content::class.java -> ContentAdapter(moshi)
            else -> null
        }

    private class ContentAdapter(moshi: Moshi) : JsonAdapter<MessageEvent.Content>() {

        private val contentTypeAdapter = moshi.adapter(MessageEvent.ContentType::class.java)
        private val textTypeAdapter = moshi.adapter(MessageEvent.Content.Text::class.java)
        private val userAddedTypeAdapter =
            moshi.adapter(MessageEvent.Content.UserAdded::class.java)
        private val userRemovedTypeAdapter =
            moshi.adapter(MessageEvent.Content.UserRemove::class.java)
        private val userJoinedAdapter = moshi.adapter(MessageEvent.Content.UserJoined::class.java)
        private val userLeftTypeAdapter = moshi.adapter(MessageEvent.Content.UserLeft::class.java)
        private val userKickedTypeAdapter =
            moshi.adapter(MessageEvent.Content.UserKicked::class.java)
        private val userBannedKickedTypeAdapter =
            moshi.adapter(MessageEvent.Content.UserBanned::class.java)
        private val channelRenamedTypeAdapter =
            moshi.adapter(MessageEvent.Content.ChannelRenamed::class.java)
        private val channelDescriptionChangedTypeAdapter =
            moshi.adapter(MessageEvent.Content.ChannelDescriptionChanged::class.java)
        private val channelIconChangedTypeAdapter =
            moshi.adapter(MessageEvent.Content.ChannelIconChanged::class.java)
        private val mapAdapter: JsonAdapter<MutableMap<String, Any?>> =
            moshi.adapter(
                Types.newParameterizedType(
                    Map::class.java,
                    String::class.java,
                    Any::class.java
                )
            )

        override fun fromJson(reader: JsonReader): MessageEvent.Content? {
            return try {
                val mapValues = mapAdapter.fromJson(reader)
                val type = mapValues?.get("type")
                when (contentTypeAdapter.fromJsonValue(type as String)) {
                    MessageEvent.ContentType.Text -> textTypeAdapter.fromJsonValue(mapValues)
                    MessageEvent.ContentType.UserAdded -> userAddedTypeAdapter.fromJsonValue(mapValues)
                    MessageEvent.ContentType.UserRemove -> userRemovedTypeAdapter.fromJsonValue(
                        mapValues
                    )
                    MessageEvent.ContentType.UserJoined -> userJoinedAdapter.fromJsonValue(mapValues)
                    MessageEvent.ContentType.UserLeft -> userLeftTypeAdapter.fromJsonValue(mapValues)
                    MessageEvent.ContentType.UserKicked -> userKickedTypeAdapter.fromJsonValue(mapValues)
                    MessageEvent.ContentType.UserBanned -> userBannedKickedTypeAdapter.fromJsonValue(
                        mapValues
                    )
                    MessageEvent.ContentType.ChannelRenamed -> channelRenamedTypeAdapter.fromJsonValue(
                        mapValues
                    )
                    MessageEvent.ContentType.ChannelDescriptionChanged -> channelDescriptionChangedTypeAdapter.fromJsonValue(
                        mapValues
                    )
                    MessageEvent.ContentType.ChannelIconChanged -> channelIconChangedTypeAdapter.fromJsonValue(
                        mapValues
                    )
                    else -> throw IllegalStateException("wrong $type passed")
                }
            } catch (exception: JsonDataException) {
                return MessageEvent.Content.Message(content = reader.nextString() as String)
            }
        }

        override fun toJson(writer: JsonWriter, value: MessageEvent.Content?) {
            writer.beginObject()
            if (value !is MessageEvent.Content.Message) {
                writer.name("type")
                writer.value(contentTypeAdapter.toJson(value?.type))
            }
            when (value) {
                is MessageEvent.Content.ChannelDescriptionChanged -> {
                    writer.name("by")
                    writer.value(value.changedBy)
                }
                is MessageEvent.Content.ChannelIconChanged -> {
                    writer.name("by")
                    writer.value(value.changedBy)
                }
                is MessageEvent.Content.ChannelRenamed -> {
                    writer.name("name")
                    writer.value(value.name)
                    writer.name("by")
                    writer.value(value.renamedBy)
                }
                is MessageEvent.Content.Message -> {
                    writer.value(value.content)
                }
                is MessageEvent.Content.Text -> {
                    writer.name("content")
                    writer.value(value.content)
                }
                is MessageEvent.Content.UserAdded -> {
                    writer.name("id")
                    writer.value(value.addedUserId)
                    writer.name("by")
                    writer.value(value.addedBy)
                }
                is MessageEvent.Content.UserBanned -> {
                    writer.name("id")
                    writer.value(value.userId)
                }
                is MessageEvent.Content.UserJoined -> {
                    writer.name("id")
                    writer.value(value.userId)
                }
                is MessageEvent.Content.UserKicked -> {
                    writer.name("id")
                    writer.value(value.userId)
                }
                is MessageEvent.Content.UserLeft -> {
                    writer.name("id")
                    writer.value(value.userId)
                }
                is MessageEvent.Content.UserRemove -> {
                    writer.name("id")
                    writer.value(value.removedUserId)
                    writer.name("by")
                    writer.value(value.removedBy)
                }
                else -> throw IllegalStateException("wrong $value passed")
            }
            writer.endObject()
        }
    }
}
