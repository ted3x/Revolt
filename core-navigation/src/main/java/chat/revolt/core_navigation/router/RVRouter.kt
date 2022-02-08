/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:12 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:12 AM
 */

package chat.revolt.core_navigation.router

import com.github.terrakok.cicerone.Screen

interface RVRouter {
    fun navigateTo(screen: Screen, navigationType: NavigationType = NavigationType.ADD)
}