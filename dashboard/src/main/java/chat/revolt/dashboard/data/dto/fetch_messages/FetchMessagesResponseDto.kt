/*
 * Created by Tedo Manvelidze(ted3x) on 2/14/22, 12:38 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/14/22, 12:38 AM
 */

package chat.revolt.dashboard.data.dto.fetch_messages

import chat.revolt.data.remote.dto.message.MessageDto
import chat.revolt.data.remote.dto.user.UserDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FetchMessagesResponseDto(
    val messages: List<MessageDto>,
    val users: List<UserDto>
)
