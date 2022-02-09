/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 12:41 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 12:41 AM
 */

package chat.revolt.auth.data.data_source

import chat.revolt.auth.data.api.SignInService
import chat.revolt.auth.data.dto.request.SignInRequestDTO
import chat.revolt.auth.data.dto.response.SignInResponseDTO
import chat.revolt.core.extensions.bodyOrThrow
import chat.revolt.core.extensions.withRetry
import retrofit2.awaitResponse

class SignInDataSourceImpl(private val service: SignInService): SignInDataSource {
    override suspend fun signIn(request: SignInRequestDTO): SignInResponseDTO {
        return withRetry {
            service.signIn(request).awaitResponse().bodyOrThrow()
        }
    }
}