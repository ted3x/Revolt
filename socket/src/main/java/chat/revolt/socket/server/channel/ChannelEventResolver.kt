/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 6:34 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 6:34 PM
 */

package chat.revolt.socket.server.channel

import chat.revolt.socket.event_resolver.EventResolver
import chat.revolt.socket.server.channel.events.ChannelStartTypingEvent
import chat.revolt.socket.server.channel.events.ChannelStopTypingEvent
import chat.revolt.socket.server.channel.type.ChannelEventType
import com.squareup.moshi.Moshi
import java.lang.Exception

class ChannelEventResolver(moshi: Moshi) : EventResolver<ChannelEvent, ChannelEventType>(moshi) {

    override fun getEventType(jsonMap: Map<String, String>): ChannelEventType? {
        return try {
            ChannelEventType.valueOf(getTypeFromJson(jsonMap))
        }
        catch (e: Exception) {
            null
        }
    }

    override fun resolveEvent(type: ChannelEventType, json: String): ChannelEvent {
        return when(type){
            ChannelEventType.ChannelCreate -> TODO()
            ChannelEventType.ChannelUpdate -> TODO()
            ChannelEventType.ChannelDelete -> TODO()
            ChannelEventType.ChannelGroupJoin -> TODO()
            ChannelEventType.ChannelGroupLeave -> TODO()
            ChannelEventType.ChannelStartTyping -> deserializeJson<ChannelStartTypingEvent>(json)
            ChannelEventType.ChannelStopTyping -> deserializeJson<ChannelStopTypingEvent>(json)
            ChannelEventType.ChannelAck -> TODO()
        }
    }

}