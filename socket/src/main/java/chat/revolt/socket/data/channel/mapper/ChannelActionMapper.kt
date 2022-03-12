/*
 * Created by Tedo Manvelidze(ted3x) on 3/12/22, 10:18 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/12/22, 10:18 PM
 */

package chat.revolt.socket.data.channel.mapper

import chat.revolt.core.mapper.Mapper
import chat.revolt.data.remote.type.EventType
import chat.revolt.domain.repository.UserRepository
import chat.revolt.socket.data.channel.ChannelActionDto
import chat.revolt.socket.domain.channel.ChannelEvents
import java.lang.IllegalStateException

class ChannelActionMapper(private val userRepository: UserRepository) :
    Mapper<ChannelActionDto, ChannelEvents> {

    override suspend fun map(from: ChannelActionDto): ChannelEvents {
        return when (from.type) {
            EventType.ChannelDelete -> ChannelEvents.ChannelDelete(channelId = from.id)
            EventType.ChannelGroupJoin -> ChannelEvents.ChannelGroupJoin(
                channelId = from.id,
                user = userRepository.getUser(from.userId!!)
            )
            EventType.ChannelGroupLeave -> ChannelEvents.ChannelGroupLeave(
                channelId = from.id,
                user = userRepository.getUser(from.userId!!)
            )
            EventType.ChannelStartTyping -> ChannelEvents.ChannelStartTyping(
                channelId = from.id,
                user = userRepository.getUser(from.userId!!)
            )
            EventType.ChannelStopTyping -> ChannelEvents.ChannelStopTyping(
                channelId = from.id,
                user = userRepository.getUser(from.userId!!)
            )
            EventType.ChannelAck -> ChannelEvents.ChannelAck(
                channelId = from.id,
                user = userRepository.getUser(from.userId!!),
                messageId = from.messageId!!
            )
            else -> throw IllegalStateException("Wrong ${from.type} passed")
        }
    }
}