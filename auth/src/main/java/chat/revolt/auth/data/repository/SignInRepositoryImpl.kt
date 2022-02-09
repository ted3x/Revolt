/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 1:17 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 1:17 AM
 */

package chat.revolt.auth.data.repository

import chat.revolt.auth.data.data_source.SignInDataSource
import chat.revolt.auth.data.mapper.SignInMapper
import chat.revolt.auth.domain.models.request.SignInRequest
import chat.revolt.auth.domain.models.response.SignInResponse
import chat.revolt.auth.domain.repository.SignInRepository

class SignInRepositoryImpl(
    private val dataSource: SignInDataSource,
    private val mapper: SignInMapper,
) : SignInRepository {
    override suspend fun signIn(request: SignInRequest): SignInResponse {
        return mapper.mapToResponse(dataSource.signIn(mapper.mapToRequest(request)))
    }
}