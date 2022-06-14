/*
 * Created by Tedo Manvelidze(ted3x) on 3/21/22, 12:08 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/21/22, 12:08 AM
 */

package chat.revolt.dashboard.data.dto.fetch_members

import chat.revolt.data.remote.dto.member.MemberDto
import chat.revolt.data.remote.dto.user.UserDto
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FetchMembersResponseDto(
    val members: List<MemberDto>,
    val users: List<UserDto>
)
