/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 6:01 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 6:01 PM
 */

package chat.revolt.domain.interactors

import chat.revolt.domain.models.User
import chat.revolt.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository): BaseUseCase<String, User?>() {

    override suspend operator fun invoke(params: String): User? {
        return repository.getUser(params)
    }
}