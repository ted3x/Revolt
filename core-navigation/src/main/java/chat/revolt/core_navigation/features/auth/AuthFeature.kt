/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:15 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:15 AM
 */

package chat.revolt.core_navigation.features.auth

sealed class AuthStates {
    object SignIn: AuthStates()
    object SignUp: AuthStates()
    object ForgotPassword: AuthStates()
}