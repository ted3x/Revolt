/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 8:17 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 8:17 PM
 */

package chat.revolt.auth.domain.interactor

import chat.revolt.auth.domain.models.response.CheckOnboardingResponse
import chat.revolt.auth.domain.repository.SignInRepository
import chat.revolt.domain.interactors.BaseUseCase

class CheckOnboardingUseCase(private val repository: SignInRepository) :
    BaseUseCase<Unit, CheckOnboardingResponse>() {
    override suspend fun invoke(params: Unit): CheckOnboardingResponse {
        //return repository.checkOnboarding()
        TODO()
    }
}