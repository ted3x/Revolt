/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:29 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:29 PM
 */

package chat.revolt.dashboard.navigator

import chat.revolt.core_navigation.features.dashboard.DashboardStates
import chat.revolt.core_navigation.router.RVRouter

class DashboardNavigatorImpl(override val router: RVRouter): DashboardNavigator {

    override fun navigateTo(state: DashboardStates) {
        when(state) {
            DashboardStates.Dashboard -> dashboard()
        }
    }

    override fun dashboard() {
        router.navigateTo(DashboardScreens.Dashboard())
    }
}