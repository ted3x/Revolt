/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:15 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:14 AM
 */

package chat.revolt.core_navigation.navigator

import chat.revolt.core_navigation.features.Feature

interface GlobalNavigator : BaseNavigator {
    fun <T> navigateTo(feature: Feature<T>)
}