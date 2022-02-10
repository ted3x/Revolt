/*
 * Created by Tedo Manvelidze(ted3x) on 2/9/22, 11:59 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/9/22, 11:59 PM
 */

package chat.revolt.app.resource_provider

import android.content.Context
import chat.revolt.core.resource_provider.ResourceProvider

class ResourceProviderImpl(private val context: Context) : ResourceProvider {

    override fun getString(stringRes: Int): String {
        return context.getString(stringRes)
    }

    override fun getString(stringRes: Int, vararg formatArgs: Any): String {
        return context.getString(stringRes, formatArgs)
    }
}