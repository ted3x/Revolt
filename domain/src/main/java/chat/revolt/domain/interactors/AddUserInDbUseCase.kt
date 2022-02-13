/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 8:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 8:24 PM
 */

package chat.revolt.domain.interactors

import chat.revolt.domain.models.User
import chat.revolt.domain.repository.UserRepository

class AddUserInDbUseCase(private val repository: UserRepository): BaseUseCase<User, Unit>() {
    override suspend fun invoke(params: User) {
        repository.addUser(params)
    }
}