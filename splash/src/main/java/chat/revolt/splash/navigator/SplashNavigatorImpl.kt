/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:55 PM
 */

package chat.revolt.splash.navigator

import chat.revolt.core_navigation.features.splash.SplashStates
import chat.revolt.core_navigation.router.RVRouter

class SplashNavigatorImpl(override val router: RVRouter) : SplashNavigator {

    override fun navigateTo(state: SplashStates) {
        when(state) {
            is SplashStates.Splash -> splash()
        }
    }

    override fun splash() {
        router.navigateTo(SplashScreens.Splash())
    }
}