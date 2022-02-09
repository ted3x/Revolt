/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 2:26 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 2:26 PM
 */

package chat.revolt.auth.navigator

import chat.revolt.core_navigation.features.auth.AuthStates
import chat.revolt.core_navigation.navigator.LocalNavigator


interface AuthNavigator: LocalNavigator<AuthStates> {
    fun signIn()
}