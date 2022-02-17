/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:19 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:16 PM
 */

package chat.revolt.socket.event_resolver

import chat.revolt.socket.Event
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

abstract class EventResolver<E : Event, T>(protected val moshi: Moshi) {
    abstract fun resolveEvent(type: T, json: String): E

    abstract fun getEventType(jsonMap: Map<String, String>): T?

    fun getTypeFromJson(jsonMap: Map<String, String>): String {
        return jsonMap["type"] ?: throw IllegalStateException("Wrong type passed")
    }


    fun resolveEventFromMap(jsonMap: Map<String, String>): E? {
        val event = getEventType(jsonMap) ?: return null
        return resolveEvent(event, mapToJson(jsonMap))
    }

    fun resolveEventFromJson(json: String): E? {
        val event = getEventType(jsonToMap(json)) ?: return null
        return resolveEvent(event, json)
    }

    private fun jsonToMap(json: String): Map<String, String> {
        val jsonAdapter = moshi.adapter<Map<String, String>>(
            Types.newParameterizedType(
                MutableMap::class.java,
                String::class.java,
                Any::class.java
            )
        )

        return jsonAdapter.fromJson(json)!!
    }

    private fun mapToJson(messageData: Map<String, String>): String {
        val jsonAdapter = moshi.adapter(Map::class.java)
        return jsonAdapter.toJson(messageData)
    }

    protected inline fun <reified T> deserializeJson(jsonString: String): T {
        val jsonAdapter = moshi.adapter(T::class.java)
        return jsonAdapter.fromJson(jsonString) ?: throw IllegalStateException("Wrong type")
    }
}