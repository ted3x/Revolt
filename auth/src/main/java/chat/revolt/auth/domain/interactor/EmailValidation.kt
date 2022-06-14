/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 12:04 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 12:04 AM
 */

package chat.revolt.auth.domain.interactor

import chat.revolt.auth.R
import chat.revolt.auth.states.EmailStates
import chat.revolt.auth.utils.isValidEmail
import chat.revolt.core.resource_provider.ResourceProvider

class EmailValidation(private val resourceProvider: ResourceProvider) {

    operator fun invoke(email: String?): EmailStates {
        return when{
            email.isNullOrBlank() -> EmailStates.Empty
            email.isValidEmail() -> EmailStates.Valid
            else -> EmailStates.Invalid(message = resourceProvider.getString(R.string.sign_in_invalid_email))
        }
    }
}