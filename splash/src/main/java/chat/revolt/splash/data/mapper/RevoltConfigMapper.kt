/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:39 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:39 PM
 */

package chat.revolt.splash.data.mapper

import chat.revolt.core.mapper.Mapper
import chat.revolt.core.server_config.RevoltConfig
import chat.revolt.splash.data.dto.RevoltConfigDto

class RevoltConfigMapper : Mapper<RevoltConfigDto, RevoltConfig> {
    override fun map(from: RevoltConfigDto): RevoltConfig {
        return RevoltConfig(
            version = from.version,
            features = from.features.mapToDomain(),
            ws = from.ws,
            app = from.app,
            vapid = from.vapid
        )
    }

    private fun RevoltConfigDto.FeaturesDto.mapToDomain() = RevoltConfig.Features(
        captcha = this.captcha.mapToDomain(),
        isEmailVerificationEnabled = this.isEmailVerificationEnabled,
        inviteOnly = this.inviteOnly,
        autumn = this.autumn.mapToDomain(),
        january = this.autumn.mapToDomain(),
        voso = this.voso.mapToDomain()
    )

    private fun RevoltConfigDto.FeaturesDto.CaptchaDto.mapToDomain() = RevoltConfig.Features.Captcha(
        enabled = this.enabled,
        siteKey = this.siteKey
    )

    private fun RevoltConfigDto.FeaturesDto.FileUploadServerDto.mapToDomain() = RevoltConfig.Features.FileUploadServer(
        enabled = this.enabled,
        url = this.url
    )

    private fun RevoltConfigDto.FeaturesDto.VoiceServerOptionsDto.mapToDomain() = RevoltConfig.Features.VoiceServerOptions(
        enabled = this.enabled,
        url = this.url,
        ws = this.ws
    )
}