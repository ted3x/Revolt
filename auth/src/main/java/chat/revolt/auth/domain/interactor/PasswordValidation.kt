/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 12:02 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 12:02 AM
 */

package chat.revolt.auth.domain.interactor

import chat.revolt.auth.R
import chat.revolt.auth.states.PasswordStates
import chat.revolt.core.resource_provider.ResourceProvider

class PasswordValidation(private val resourceProvider: ResourceProvider) {

    fun invoke(password: String?): PasswordStates {
        return when {
            password == null || password.length < 8 -> PasswordStates.Short(
                message = resourceProvider.getString(
                    R.string.sign_in_too_short
                )
            )
            password.length > 1024 ->
                PasswordStates.Short(
                    message = resourceProvider.getString(
                        R.string.sign_in_too_long
                    )
                )
            else -> PasswordStates.Valid
        }
    }
}