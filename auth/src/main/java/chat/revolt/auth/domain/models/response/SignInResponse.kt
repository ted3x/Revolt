/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 8:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 8:14 PM
 */

package chat.revolt.auth.domain.models.response

import chat.revolt.domain.models.Account

data class SignInResponse(
    val id: String,
    val userId: String,
    val token: String,
    val name: String,
    val subscription: String?
) {
    fun mapToAccount() = Account(
        userId = userId,
        token = token,
        name = name
    )
}