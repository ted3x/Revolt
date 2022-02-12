/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 8:57 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 8:57 PM
 */

package chat.revolt.core.server_config

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class RevoltConfig(
    @Json(name = "revolt")
    val version: String,
    val features: Features,
    val ws: String,
    val app: String,
    val vapid: String,
) {
    @JsonClass(generateAdapter = true)
    data class Features(
        val captcha: Captcha,
        @Json(name = "email")
        val isEmailVerificationEnabled: Boolean,
        @Json(name = "invite_only")
        val inviteOnly: Boolean,
        val autumn: FileUploadServer,
        val january: FileUploadServer,
        val voso: VoiceServerOptions
    ) {
        data class Captcha(
            val enabled: Boolean,
            @Json(name = "key")
            val siteKey: String
        )

        data class FileUploadServer(
            val enabled: Boolean,
            val url: String
        )

        data class VoiceServerOptions(
            val enabled: Boolean,
            val url: String,
            val ws: String
        )
    }
}