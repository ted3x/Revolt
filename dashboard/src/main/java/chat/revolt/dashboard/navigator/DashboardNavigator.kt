/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:28 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:28 PM
 */

package chat.revolt.dashboard.navigator

import chat.revolt.core_navigation.features.dashboard.DashboardStates
import chat.revolt.core_navigation.navigator.LocalNavigator

interface DashboardNavigator: LocalNavigator<DashboardStates> {

    fun dashboard()
}