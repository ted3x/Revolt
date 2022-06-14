/*
 * Created by Tedo Manvelidze(ted3x) on 3/21/22, 12:07 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/21/22, 12:07 AM
 */

package chat.revolt.dashboard.data

import chat.revolt.dashboard.data.dto.fetch_members.FetchMembersResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ServerService {

    @GET("servers/{serverId}/members")
    fun fetchMembers(@Path(value = "serverId") serverId: String): Call<FetchMembersResponseDto>
}