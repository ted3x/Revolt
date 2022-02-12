/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:35 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:35 PM
 */

package chat.revolt.splash.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RevoltConfigDto(
    @Json(name = "revolt")
    val version: String,
    val features: FeaturesDto,
    val ws: String,
    val app: String,
    val vapid: String,
) {
    @JsonClass(generateAdapter = true)
    data class FeaturesDto(
        val captcha: CaptchaDto,
        @Json(name = "email")
        val isEmailVerificationEnabled: Boolean,
        @Json(name = "invite_only")
        val inviteOnly: Boolean,
        val autumn: FileUploadServerDto,
        val january: FileUploadServerDto,
        val voso: VoiceServerOptionsDto
    ) {
        data class CaptchaDto(
            val enabled: Boolean,
            @Json(name = "key")
            val siteKey: String
        )

        data class FileUploadServerDto(
            val enabled: Boolean,
            val url: String
        )

        data class VoiceServerOptionsDto(
            val enabled: Boolean,
            val url: String,
            val ws: String
        )
    }
}