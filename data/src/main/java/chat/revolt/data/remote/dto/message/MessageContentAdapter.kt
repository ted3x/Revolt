/*
 * Created by Tedo Manvelidze(ted3x) on 2/19/22, 11:02 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/19/22, 11:02 PM
 */

package chat.revolt.data.remote.dto.message

import com.squareup.moshi.*
import java.lang.IllegalStateException
import java.lang.reflect.Type

class MessageContentAdapter : JsonAdapter.Factory {
    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? =
        when (type) {
            MessageDto.ContentDto::class.java -> AnimalAdapter(moshi)
            else -> null
        }

    private class AnimalAdapter(moshi: Moshi) : JsonAdapter<MessageDto.ContentDto>() {

        private val contentTypeAdapter = moshi.adapter(MessageDto.ContentTypeDto::class.java)
        private val textTypeAdapter = moshi.adapter(MessageDto.ContentDto.Text::class.java)
        private val userAddedTypeAdapter =
            moshi.adapter(MessageDto.ContentDto.UserAdded::class.java)
        private val userRemovedTypeAdapter =
            moshi.adapter(MessageDto.ContentDto.UserRemove::class.java)
        private val userJoinedAdapter = moshi.adapter(MessageDto.ContentDto.UserJoined::class.java)
        private val userLeftTypeAdapter = moshi.adapter(MessageDto.ContentDto.UserLeft::class.java)
        private val userKickedTypeAdapter =
            moshi.adapter(MessageDto.ContentDto.UserKicked::class.java)
        private val userBannedKickedTypeAdapter =
            moshi.adapter(MessageDto.ContentDto.UserBanned::class.java)
        private val channelRenamedTypeAdapter =
            moshi.adapter(MessageDto.ContentDto.ChannelRenamed::class.java)
        private val channelDescriptionChangedTypeAdapter =
            moshi.adapter(MessageDto.ContentDto.ChannelDescriptionChanged::class.java)
        private val channelIconChangedTypeAdapter =
            moshi.adapter(MessageDto.ContentDto.ChannelIconChanged::class.java)
        private val mapAdapter: JsonAdapter<MutableMap<String, Any?>> =
            moshi.adapter(
                Types.newParameterizedType(
                    Map::class.java,
                    String::class.java,
                    Any::class.java
                )
            )

        override fun fromJson(reader: JsonReader): MessageDto.ContentDto? {
            return try {
                val mapValues = mapAdapter.fromJson(reader)
                val type = mapValues?.get("type")
                when (contentTypeAdapter.fromJsonValue(type as String)) {
                    MessageDto.ContentTypeDto.Text -> textTypeAdapter.fromJsonValue(mapValues)
                    MessageDto.ContentTypeDto.UserAdded -> userAddedTypeAdapter.fromJsonValue(mapValues)
                    MessageDto.ContentTypeDto.UserRemove -> userRemovedTypeAdapter.fromJsonValue(
                        mapValues
                    )
                    MessageDto.ContentTypeDto.UserJoined -> userJoinedAdapter.fromJsonValue(mapValues)
                    MessageDto.ContentTypeDto.UserLeft -> userLeftTypeAdapter.fromJsonValue(mapValues)
                    MessageDto.ContentTypeDto.UserKicked -> userKickedTypeAdapter.fromJsonValue(mapValues)
                    MessageDto.ContentTypeDto.UserBanned -> userBannedKickedTypeAdapter.fromJsonValue(
                        mapValues
                    )
                    MessageDto.ContentTypeDto.ChannelRenamed -> channelRenamedTypeAdapter.fromJsonValue(
                        mapValues
                    )
                    MessageDto.ContentTypeDto.ChannelDescriptionChanged -> channelDescriptionChangedTypeAdapter.fromJsonValue(
                        mapValues
                    )
                    MessageDto.ContentTypeDto.ChannelIconChanged -> channelIconChangedTypeAdapter.fromJsonValue(
                        mapValues
                    )
                    else -> throw IllegalStateException("wrong $type passed")
                }
            } catch (exception: JsonDataException) {
                return MessageDto.ContentDto.Message(content = reader.nextString() as String)
            }
        }

        override fun toJson(writer: JsonWriter, value: MessageDto.ContentDto?) {
            writer.beginObject()
            if (value !is MessageDto.ContentDto.Message) {
                writer.name("type")
                writer.value(contentTypeAdapter.toJson(value?.type))
            }
            when (value) {
                is MessageDto.ContentDto.ChannelDescriptionChanged -> {
                    writer.name("by")
                    writer.value(value.changedBy)
                }
                is MessageDto.ContentDto.ChannelIconChanged -> {
                    writer.name("by")
                    writer.value(value.changedBy)
                }
                is MessageDto.ContentDto.ChannelRenamed -> {
                    writer.name("name")
                    writer.value(value.name)
                    writer.name("by")
                    writer.value(value.renamedBy)
                }
                is MessageDto.ContentDto.Message -> {
                    writer.value(value.content)
                }
                is MessageDto.ContentDto.Text -> {
                    writer.name("content")
                    writer.value(value.content)
                }
                is MessageDto.ContentDto.UserAdded -> {
                    writer.name("id")
                    writer.value(value.addedUserId)
                    writer.name("by")
                    writer.value(value.addedBy)
                }
                is MessageDto.ContentDto.UserBanned -> {
                    writer.name("id")
                    writer.value(value.userId)
                }
                is MessageDto.ContentDto.UserJoined -> {
                    writer.name("id")
                    writer.value(value.userId)
                }
                is MessageDto.ContentDto.UserKicked -> {
                    writer.name("id")
                    writer.value(value.userId)
                }
                is MessageDto.ContentDto.UserLeft -> {
                    writer.name("id")
                    writer.value(value.userId)
                }
                is MessageDto.ContentDto.UserRemove -> {
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
