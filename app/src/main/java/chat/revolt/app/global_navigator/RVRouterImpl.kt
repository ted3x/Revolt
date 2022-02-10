/*
 * Created by Tedo Manvelidze(ted3x) on 2/10/22, 2:12 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/10/22, 2:12 PM
 */

package chat.revolt.app.global_navigator

import chat.revolt.core_navigation.router.NavigationType
import chat.revolt.core_navigation.router.RVRouter
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

class RVRouterImpl : Router(), RVRouter {
    override fun navigateTo(screen: Screen, navigationType: NavigationType) {
        when(navigationType) {
            NavigationType.ADD -> navigateTo(screen)
            NavigationType.REPLACE -> replaceScreen(screen)
            NavigationType.REPLACE_AND_CLEAR -> newRootScreen(screen)
        }
    }

}