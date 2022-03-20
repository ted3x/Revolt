/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 1:08 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 1:08 AM
 */

package chat.revolt.domain.models

import chat.revolt.domain.UlidTimeDecoder
import kotlin.math.abs

data class Message(
    val id: String,
    val channel: String,
    val authorId: String,
    val content: Content,
    val attachments: List<Attachment>?,
    val edited: String?,
    val mentions: List<String>?,
    val replies: List<String>?,
    val masquerade: Masquerade?
) {

    val timestamp = UlidTimeDecoder.getTimestamp(id)

    sealed interface SystemMessage {
        val message: String
        val authorImageUrl: String
    }

    sealed class Content(val type: ContentType) {

        data class Message(val content: String) : Content(ContentType.Message)

        data class Text(val content: String) : Content(ContentType.Text)

        data class UserAdded(
            val addedUserId: String,
            val addedUsername: String,
            val addedBy: String,
            val addedByUsername: String
        ) :
            Content(ContentType.UserAdded), SystemMessage {
            override val authorImageUrl: String = ""
            override val message: String = "$addedUsername was added by $addedByUsername"
        }

        data class UserRemove(
            val removedUserId: String,
            val removedUsername: String,
            val removedById: String,
            val removedByUsername: String,
        ) :
            Content(ContentType.UserRemove), SystemMessage {
            override val message: String =
                "$removedUsername was removed by $removedByUsername"
            override val authorImageUrl: String = ""
        }

        data class UserJoined(
            val userId: String,
            val username: String,
            val userAvatarUrl: String
        ) : Content(ContentType.UserJoined), SystemMessage {
            override val message: String = "$username joined"
            override val authorImageUrl: String = userAvatarUrl
        }

        data class UserLeft(
            val userId: String,
            val username: String,
            val userAvatarUrl: String
        ) : Content(ContentType.UserLeft), SystemMessage {
            override val message: String = "$username left"
            override val authorImageUrl: String = userAvatarUrl
        }

        data class UserKicked(
            val userId: String,
            val username: String,
            val userAvatarUrl: String
        ) : Content(ContentType.UserKicked), SystemMessage {
            override val message: String = "$username was kicked"
            override val authorImageUrl: String = userAvatarUrl
        }

        data class UserBanned(
            val userId: String,
            val username: String,
            val userAvatarUrl: String
        ) : Content(ContentType.UserBanned), SystemMessage {
            override val message: String = "$username was banned"
            override val authorImageUrl: String = userAvatarUrl
        }

        data class ChannelRenamed(
            val name: String,
            val renamedById: String,
            val renamedByUsername: String,
            val renamedByAvatarUrl: String,
        ) :
            Content(ContentType.ChannelRenamed), SystemMessage {
            override val message: String = "$renamedByUsername renamed channel to $name"
            override val authorImageUrl: String = renamedByAvatarUrl
        }

        data class ChannelDescriptionChanged(
            val changedById: String,
            val changedByUsername: String,
            val changedByAvatarUrl: String
        ) :
            Content(ContentType.ChannelDescriptionChanged), SystemMessage {
            override val message: String = "$changedByUsername changed channel description"
            override val authorImageUrl: String = changedByAvatarUrl
        }

        data class ChannelIconChanged(
            val changedById: String,
            val changedByUsername: String,
            val changedByAvatarUrl: String
        ) :
            Content(ContentType.ChannelIconChanged), SystemMessage {
            override val message: String = "$changedByUsername changed channel icon"
            override val authorImageUrl: String = changedByAvatarUrl
        }
    }

    data class Masquerade(
        val name: String? = null,
        val avatar: String? = null
    )

    fun isDivided(previousMessage: Message): Boolean {
        return (previousMessage.authorId != authorId || (previousMessage.authorId == authorId && abs(
            previousMessage.timestamp - timestamp
        ) > DIVIDER_MAX_TIME))
    }

    companion object {
        private const val DIVIDER_MAX_TIME = 420000
        val EMPTY =
            Message(
                id = "",
                channel = "",
                authorId = "",
                content = Content.Message(""),
                attachments = null,
                edited = null,
                mentions = null,
                replies = null,
                masquerade = null,
            )
    }
}