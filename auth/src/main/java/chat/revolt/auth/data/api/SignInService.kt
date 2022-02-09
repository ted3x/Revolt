/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 8:11 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 8:11 PM
 */

package chat.revolt.auth.data.api

import chat.revolt.auth.data.dto.request.SignInRequestDTO
import chat.revolt.auth.data.dto.response.SignInResponseDTO
import retrofit2.Call
import retrofit2.http.POST

interface SignInService {

    @POST("auth/sessions/login")
    suspend fun signIn(request: SignInRequestDTO): Call<SignInResponseDTO>
}