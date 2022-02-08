/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:17 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:17 AM
 */

package chat.revolt.app.global_navigator

import chat.revolt.core_navigation.features.Feature
import chat.revolt.core_navigation.navigator.GlobalNavigator
import chat.revolt.core_navigation.router.RVRouter

class GlobalNavigatorImpl(override val router: RVRouter) : GlobalNavigator {
    override fun <T> navigateTo(feature: Feature<T>) {
        when (feature) {
            is Feature.Auth -> TODO()
        }
    }

}