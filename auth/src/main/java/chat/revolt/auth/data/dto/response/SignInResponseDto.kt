/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 8:14 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 8:14 PM
 */

package chat.revolt.auth.data.dto.response

import com.squareup.moshi.Json

data class SignInResponseDto(
    @Json(name = "_id")
    val id: String?,
    @Json(name = "user_id")
    val userId: String,
    val token: String,
    val name: String,
    val subscription: String?
)