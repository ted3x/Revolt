/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 8:13 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 8:13 PM
 */

package chat.revolt.auth.data.dto.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckOnboardingResponseDto(val onboarding: Boolean?)
