/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 12:37 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 12:37 AM
 */

package chat.revolt.auth.data.data_source

import chat.revolt.auth.data.dto.request.SignInRequestDto
import chat.revolt.auth.data.dto.response.SignInResponseDto

interface SignInDataSource {

   suspend fun signIn(request: SignInRequestDto): SignInResponseDto
}