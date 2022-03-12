/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 8:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 8:14 PM
 */

package chat.revolt.auth.domain.models.response

import com.squareup.moshi.Json

data class CheckOnboardingResponse(val isOnboarded: Boolean)