/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 8:41 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 8:37 PM
 */

package chat.revolt.data.remote.service

import chat.revolt.data.remote.dto.user.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): Call<UserDto>
}