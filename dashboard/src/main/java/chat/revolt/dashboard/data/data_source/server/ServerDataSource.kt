/*
 * Created by Tedo Manvelidze(ted3x) on 3/21/22, 12:08 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/21/22, 12:08 AM
 */

package chat.revolt.dashboard.data.data_source.server

import chat.revolt.dashboard.data.dto.fetch_members.FetchMembersRequestDto
import chat.revolt.dashboard.data.dto.fetch_members.FetchMembersResponseDto

interface ServerDataSource {

    suspend fun fetchMembers(request: FetchMembersRequestDto): FetchMembersResponseDto
}