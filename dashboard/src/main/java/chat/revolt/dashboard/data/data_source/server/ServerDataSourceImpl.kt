/*
 * Created by Tedo Manvelidze(ted3x) on 3/21/22, 12:08 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/21/22, 12:08 AM
 */

package chat.revolt.dashboard.data.data_source.server

import chat.revolt.core.extensions.awaitResult
import chat.revolt.dashboard.data.ServerService
import chat.revolt.dashboard.data.dto.fetch_members.FetchMembersRequestDto
import chat.revolt.dashboard.data.dto.fetch_members.FetchMembersResponseDto

class ServerDataSourceImpl(private val service: ServerService): ServerDataSource {

    override suspend fun fetchMembers(request: FetchMembersRequestDto): FetchMembersResponseDto {
        return service.fetchMembers(request.serverId).awaitResult()
    }
}