/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 8:15 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 8:15 PM
 */

package chat.revolt.auth.data.mapper

import chat.revolt.auth.data.dto.response.CheckOnboardingResponseDto
import chat.revolt.auth.domain.models.response.CheckOnboardingResponse
import chat.revolt.core.mapper.Mapper

class CheckOnboardingMapper : Mapper<CheckOnboardingResponseDto, CheckOnboardingResponse> {

    override suspend fun map(from: CheckOnboardingResponseDto): CheckOnboardingResponse {
        return CheckOnboardingResponse(isOnboarded = from.onboarding ?: true)
    }
}