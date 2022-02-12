/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 12:41 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 12:41 AM
 */

package chat.revolt.auth.data.data_source

import chat.revolt.auth.data.api.SignInService
import chat.revolt.auth.data.dto.request.SignInRequestDto
import chat.revolt.auth.data.dto.response.SignInResponseDto
import chat.revolt.core.extensions.awaitResult

class SignInDataSourceImpl(private val service: SignInService): SignInDataSource {
    override suspend fun signIn(request: SignInRequestDto): SignInResponseDto {
        return service.signIn(request).awaitResult()
    }
}