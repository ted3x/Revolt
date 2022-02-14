/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 10:33 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 10:33 PM
 */

package chat.revolt.domain.models

sealed class ChatItem(open val id: String, author: User) {
    data class Text(override val id: String, val text: String, val author: User): ChatItem(id, author)
    data class Image(override val id: String, val imageUrl: String, val author: User): ChatItem(id, author)
    data class Video(override val id: String, val videoUrl: String, val author: User): ChatItem(id, author)
}
