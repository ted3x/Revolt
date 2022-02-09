/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 8:16 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 8:14 PM
 */

package chat.revolt.auth.domain.models.request

data class SignInRequest(
    val email: String,
    val password: String,
    val challenge: String? = null,
    val friendlyName: String = "Android",
    val captcha: String
)