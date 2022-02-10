/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 8:41 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 8:38 PM
 */

package chat.revolt.data.remote.data_source

import chat.revolt.core.extensions.bodyOrThrow
import chat.revolt.data.remote.dto.user.UserDto
import chat.revolt.data.remote.service.UserService
import retrofit2.awaitResponse

class UserDataSourceImpl(private val service: UserService): UserDataSource {
    override suspend fun getUser(userId: String): UserDto {
        return service.getUser(userId).awaitResponse().bodyOrThrow()
    }
}