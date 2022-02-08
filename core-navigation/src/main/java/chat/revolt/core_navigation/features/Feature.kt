/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 1:14 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 1:14 AM
 */

package chat.revolt.core_navigation.features

import chat.revolt.core_navigation.features.auth.AuthStates

sealed class Feature<T>(open val state: T) {
    data class Auth(override val state: AuthStates): Feature<AuthStates>(state)
}

