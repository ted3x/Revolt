/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 2:26 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 2:25 PM
 */

package chat.revolt.auth.navigator

import chat.revolt.core_navigation.features.auth.AuthStates
import chat.revolt.core_navigation.router.RVRouter

class AuthNavigatorImpl(override val router: RVRouter): AuthNavigator {

    override fun navigateTo(state: AuthStates) {
        when(state) {
            is AuthStates.SignIn -> signIn()
            else -> TODO()
        }
    }

    override fun signIn() {
        router.navigateTo(AuthScreens.SignIn())
    }

}