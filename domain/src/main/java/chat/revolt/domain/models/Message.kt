/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 1:08 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 1:08 AM
 */

package chat.revolt.domain.models

data class Message(
    val id: String,
    val channel: String,
    val author: User,
    val content: Content,
    val attachments: List<Attachment>?,
    val edited: String?,
    val mentions: List<User>?,
    val replies: List<String>?,
    val masquerade: Masquerade?,
    val isLoadingItem: Boolean = false
) {

    val authorName = masquerade?.name ?: author.username

    sealed interface SystemMessage {
        val message: String
        val authorImageUrl: String
    }

    sealed class Content(val type: ContentType) {

        data class Message(val content: String) : Content(ContentType.Message)

        data class Text(val content: String) : Content(ContentType.Text)

        data class UserAdded(
            val addedUser: User,
            val addedBy: User
        ) :
            Content(ContentType.UserAdded), SystemMessage {
            override val authorImageUrl: String = ""
            override val message: String = "${addedUser.username} was added by ${addedBy.username}"
        }

        data class UserRemove(
            val removedUser: User,
            val removedBy: User
        ) :
            Content(ContentType.UserRemove), SystemMessage {
            override val message: String =
                "${removedUser.username} was removed by ${removedBy.username}"
            override val authorImageUrl: String = ""
        }

        data class UserJoined(
            val user: User
        ) : Content(ContentType.UserJoined), SystemMessage {
            override val message: String = "${user.username} joined"
            override val authorImageUrl: String = user.avatarUrl
        }

        data class UserLeft(
            val user: User
        ) : Content(ContentType.UserLeft), SystemMessage {
            override val message: String = "${user.username} left"
            override val authorImageUrl: String = user.avatarUrl
        }

        data class UserKicked(
            val user: User
        ) : Content(ContentType.UserKicked), SystemMessage {
            override val message: String = "${user.username} was kicked"
            override val authorImageUrl: String = user.avatarUrl
        }

        data class UserBanned(
            val user: User
        ) : Content(ContentType.UserBanned), SystemMessage {
            override val message: String = "${user.username} was banned"
            override val authorImageUrl: String = user.avatarUrl
        }

        data class ChannelRenamed(
            val name: String,
            val renamedBy: User
        ) :
            Content(ContentType.ChannelRenamed), SystemMessage {
            override val message: String = "${renamedBy.username} renamed channel to $name"
            override val authorImageUrl: String = renamedBy.avatarUrl
        }

        data class ChannelDescriptionChanged(
            val changedBy: User
        ) :
            Content(ContentType.ChannelDescriptionChanged), SystemMessage {
            override val message: String = "${changedBy.username} changed channel description"
            override val authorImageUrl: String = changedBy.avatarUrl
        }

        data class ChannelIconChanged(
            val changedBy: User
        ) :
            Content(ContentType.ChannelIconChanged), SystemMessage {
            override val message: String = "${changedBy.username} changed channel icon"
            override val authorImageUrl: String = changedBy.avatarUrl
        }
    }

    data class Attachment(
        val id: String?,
        val tag: String,
        val size: String?,
        val filename: String?,
        val metadata: Metadata?,
        val contentType: String?
    ) {
        data class Metadata(
            val value: String?,
            val width: Int?,
            val height: Int?
        )
    }

    data class Masquerade(
        val name: String? = null,
        val avatar: String? = null
    )
}