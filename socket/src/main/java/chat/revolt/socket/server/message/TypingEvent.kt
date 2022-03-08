/*
 * Created by Tedo Manvelidze(ted3x) on 2/19/22, 12:28 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/19/22, 12:28 AM
 */

package chat.revolt.socket.server.message

import chat.revolt.core.mapper.Mapper
import chat.revolt.domain.models.User
import chat.revolt.domain.repository.UserRepository
import chat.revolt.socket.events.Event
import chat.revolt.socket.events.Events
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChannelStartTypingEvent(
    @Json(name = "id")
    val channelId: String,
    @Json(name = "user")
    val userId: String
) : Events() {
    override val type: Event = Event.ChannelStartTyping
}

data class ChannelStartTyping(
    val channelId: String,
    val user: User,
    val isCurrentUser: Boolean
)

class ChannelStartTypingEventMapper(private val userRepository: UserRepository) :
    Mapper<ChannelStartTypingEvent, ChannelStartTyping> {
    override suspend fun map(from: ChannelStartTypingEvent): ChannelStartTyping {
        val user = userRepository.getUser(from.userId)
        return ChannelStartTyping(
            channelId = from.channelId,
            user = userRepository.getUser(from.userId),
            isCurrentUser = userRepository.getCurrentUser()?.id == user.id
        )
    }

}


@JsonClass(generateAdapter = true)
data class ChannelStopTypingEvent(
    @Json(name = "id")
    val channelId: String,
    @Json(name = "user")
    val userId: String
) : Events() {
    override val type: Event = Event.ChannelStopTyping
}

data class ChannelStopTyping(
    val channelId: String,
    val user: User
)

class ChannelStopTypingEventMapper(private val userRepository: UserRepository) :
    Mapper<ChannelStopTypingEvent, ChannelStopTyping> {
    override suspend fun map(from: ChannelStopTypingEvent): ChannelStopTyping {
        return ChannelStopTyping(
            channelId = from.channelId,
            user = userRepository.getUser(from.userId)
        )
    }

}