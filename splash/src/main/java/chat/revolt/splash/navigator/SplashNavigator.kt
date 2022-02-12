/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:55 PM
 */

package chat.revolt.splash.navigator

import chat.revolt.core_navigation.features.splash.SplashStates
import chat.revolt.core_navigation.navigator.LocalNavigator

interface SplashNavigator: LocalNavigator<SplashStates> {
    fun splash()
}