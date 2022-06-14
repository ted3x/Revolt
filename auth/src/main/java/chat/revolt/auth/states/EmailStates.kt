/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 10:17 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 10:17 PM
 */

package chat.revolt.auth.states

sealed class EmailStates {
    object Empty : EmailStates()
    object Valid : EmailStates()
    data class Invalid(val message: String) : EmailStates()

    val isValid get() = this is Valid
}