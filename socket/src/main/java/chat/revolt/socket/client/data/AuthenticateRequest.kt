/*
 * Created by Tedo Manvelidze(ted3x) on 2/18/22, 1:26 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/18/22, 1:26 AM
 */

package chat.revolt.socket.client.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticateRequest(
    val type: String = "Authenticate",
    val token: String
)