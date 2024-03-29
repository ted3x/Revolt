/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:46 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:46 AM
 */

package chat.revolt.dashboard.domain.models.fetch_messages

import chat.revolt.domain.models.Message
import chat.revolt.domain.models.User
import chat.revolt.domain.models.member.Member

data class FetchMessagesResponse(
    val messages: List<Message>,
    val users: List<User>,
    val members: List<Member>?
)