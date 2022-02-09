/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 1:11 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 1:11 AM
 */

package chat.revolt.auth.domain.repository

import chat.revolt.auth.domain.models.request.SignInRequest
import chat.revolt.auth.domain.models.response.SignInResponse

interface SignInRepository {

    suspend fun signIn(request: SignInRequest): SignInResponse
}