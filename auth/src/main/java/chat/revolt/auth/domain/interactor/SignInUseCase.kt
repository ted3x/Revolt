/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 1:07 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 1:07 AM
 */

package chat.revolt.auth.domain.interactor

import chat.revolt.auth.domain.models.request.SignInRequest
import chat.revolt.auth.domain.models.response.SignInResponse
import chat.revolt.auth.domain.repository.SignInRepository
import chat.revolt.domain.interactors.BaseUseCase

class SignInUseCase(private val repository: SignInRepository) :
    BaseUseCase<SignInRequest, SignInResponse>() {

    override suspend operator fun invoke(params: SignInRequest): SignInResponse {
        return repository.signIn(params)
    }
}
