/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 1:22 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 1:22 AM
 */

package chat.revolt.auth.data.mapper

import chat.revolt.auth.data.dto.request.SignInRequestDTO
import chat.revolt.auth.data.dto.response.SignInResponseDTO
import chat.revolt.auth.domain.models.request.SignInRequest
import chat.revolt.auth.domain.models.response.SignInResponse
import chat.revolt.core.mapper.ServiceMapper

class SignInMapper :
    ServiceMapper<SignInRequestDTO, SignInRequest, SignInResponseDTO, SignInResponse> {

    override fun mapToRequest(from: SignInRequest): SignInRequestDTO {
        return SignInRequestDTO(
            email = from.email,
            password = from.password,
            challenge = from.challenge,
            friendlyName = from.friendlyName,
            captcha = from.captcha
        )
    }

    override fun mapToResponse(from: SignInResponseDTO): SignInResponse {
        return SignInResponse(
            id = from.id,
            userId = from.userId,
            token = from.token,
            name = from.name,
            subscription = from.subscription
        )
    }
}