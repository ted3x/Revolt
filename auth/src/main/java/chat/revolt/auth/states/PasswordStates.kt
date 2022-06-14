/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 10:27 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 10:27 PM
 */

package chat.revolt.auth.states

sealed class PasswordStates {
    object Empty : PasswordStates()
    object Valid: PasswordStates()
    data class Short(val message: String): PasswordStates()
    data class Long(val message: String): PasswordStates()

    val isValid get() = this is Valid
}
