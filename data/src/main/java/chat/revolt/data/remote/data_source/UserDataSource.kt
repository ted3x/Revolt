/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 8:42 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 8:38 PM
 */

package chat.revolt.data.remote.data_source

import chat.revolt.data.remote.dto.user.UserDto

interface UserDataSource {

    suspend fun getUser(userId: String): UserDto?
}