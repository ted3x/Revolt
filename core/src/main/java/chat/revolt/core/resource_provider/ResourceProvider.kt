/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 11:58 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 11:58 PM
 */

package chat.revolt.core.resource_provider

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes stringRes: Int): String
    fun getString(@StringRes stringRes: Int, vararg formatArgs: Any): String
}