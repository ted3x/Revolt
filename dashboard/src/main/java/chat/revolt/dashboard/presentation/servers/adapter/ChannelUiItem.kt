/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 5:35 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 5:35 PM
 */

package chat.revolt.dashboard.presentation.servers.adapter

import kotlin.random.Random


sealed class ChannelUiItem(
    open val id: String, open val name: String, open var isVisible: Boolean
) {
    data class Channel(
        override val id: String,
        override val name: String,
        val description: String?,
        val iconUrl: String?,
        val categoryId: String?,
        override var isVisible: Boolean,
        var isSelected: Boolean
    ) : ChannelUiItem(id, name, isVisible)

    data class Category(
        override val id: String,
        override val name: String,
        override var isVisible: Boolean = true
    ) : ChannelUiItem(id, name, isVisible)

    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return Random.nextInt()
    }
}