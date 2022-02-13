/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:17 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:17 AM
 */

package chat.revolt.app.global_navigator

import chat.revolt.auth.navigator.AuthNavigator
import chat.revolt.core_navigation.features.Feature
import chat.revolt.core_navigation.navigator.GlobalNavigator
import chat.revolt.core_navigation.router.RVRouter
import chat.revolt.dashboard.navigator.DashboardNavigator
import chat.revolt.splash.navigator.SplashNavigator
import org.koin.java.KoinJavaComponent.get

class GlobalNavigatorImpl(override val router: RVRouter) : GlobalNavigator {
    override fun <T> navigateTo(feature: Feature<T>) {
        when (feature) {
            is Feature.Splash -> get<SplashNavigator>(SplashNavigator::class.java).navigateTo(feature.state)
            is Feature.Auth -> get<AuthNavigator>(AuthNavigator::class.java).navigateTo(feature.state)
            is Feature.Dashboard -> get<DashboardNavigator>(DashboardNavigator::class.java).navigateTo(feature.state)
        }
    }

}