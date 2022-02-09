/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 8:16 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 8:14 PM
 */

package chat.revolt.auth.data.dto.request

import com.squareup.moshi.Json

data class SignInRequestDTO(
    val email: String,
    val password: String,
    val challenge: String? = null,
    @Json(name = "friendly_name")
    val friendlyName: String = "Android",
    val captcha: String
)