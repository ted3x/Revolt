/*
 * Created by Tedo Manvelidze(ted3x) on 3/21/22, 12:10 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/21/22, 12:10 AM
 */

package chat.revolt.dashboard.domain.models.fetch_members

import chat.revolt.domain.models.User
import chat.revolt.domain.models.member.Member

data class FetchMembersResponse(
    val members: List<Member>,
    val users: List<User>
)
