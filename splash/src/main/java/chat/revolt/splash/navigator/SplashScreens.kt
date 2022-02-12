/*
 * Created by Tedo Manvelidze(ted3x) on 2/12/22, 11:55 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/12/22, 11:55 PM
 */

package chat.revolt.splash.navigator

import chat.revolt.splash.presentation.ui.SplashFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object SplashScreens {
    fun Splash() = FragmentScreen { SplashFragment() }
}