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
    val masquerade: Masquerade?
) {

    val authorName = masquerade?.name ?: author.username
    sealed class Content(val type: ContentType) {

        data class Message(val content: String) : Content(ContentType.Message)

        data class Text(val content: String) : Content(ContentType.Text)

        data class UserAdded(
            val addedUser: User,
            val addedBy: User
        ) :
            Content(ContentType.UserAdded)

        data class UserRemove(
            val removedUser: User,
            val removedBy: User
        ) :
            Content(ContentType.UserRemove)

        data class UserJoined(
            val user: User
        ) : Content(ContentType.UserJoined)

        data class UserLeft(
            val user: User
        ) : Content(ContentType.UserLeft)

        data class UserKicked(
            val user: User
        ) : Content(ContentType.UserKicked)

        data class UserBanned(
            val user: User
        ) : Content(ContentType.UserBanned)

        data class ChannelRenamed(
            val name: String,
            val renamedBy: User
        ) :
            Content(ContentType.ChannelRenamed)

        data class ChannelDescriptionChanged(
            val changedBy: User) :
            Content(ContentType.ChannelDescriptionChanged)

        data class ChannelIconChanged(
            val changedBy: User) :
            Content(ContentType.ChannelIconChanged)
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