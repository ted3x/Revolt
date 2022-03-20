/*
 * Created by Tedo Manvelidze(ted3x) on 3/20/22, 10:45 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/20/22, 10:45 PM
 */

package chat.revolt.domain.models.member

import chat.revolt.domain.models.Attachment

data class Member(
    val id: Identifier,
    val nickname: String?,
    val avatar: Attachment?,
    val roles: List<String>?
) {
    data class Identifier(
        val serverId: String,
        val userId: String
    )
}
