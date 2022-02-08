/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:14 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:11 AM
 */

package chat.revolt.core_navigation.navigator

interface LocalNavigator <T>: BaseNavigator {
    fun navigateTo(state: T)
}