/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 4:26 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 4:26 PM
 */

package chat.revolt.dashboard.presentation.servers.adapter.channels

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import chat.revolt.dashboard.presentation.servers.adapter.ChannelUiItem

class ChannelsAdapter(
    private val onCategoryVisibilityChange: (categoryId: String, isVisible: Boolean) -> Unit,
    private val onChannelClick: (channelId: String) -> Unit
) :
    ListAdapter<ChannelUiItem, BaseChannelViewHolder<*, in ChannelUiItem>>(COMPARATOR) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseChannelViewHolder<*, in ChannelUiItem> {
        return when (viewType) {
            CHANNEL -> ChannelViewHolder(
                parent,
                onChannelClick
            ) as BaseChannelViewHolder<*, in ChannelUiItem>
            else -> CategoryViewHolder(
                parent,
                onCategoryVisibilityChange
            ) as BaseChannelViewHolder<*, in ChannelUiItem>
        }
    }

    override fun onBindViewHolder(
        holder: BaseChannelViewHolder<*, in ChannelUiItem>,
        position: Int
    ) {
        holder.onBind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is ChannelUiItem.Channel) 4 else 3
    }

    companion object {

        private const val CATEGORY = 3
        private const val CHANNEL = 4
        private val COMPARATOR = object : DiffUtil.ItemCallback<ChannelUiItem>() {
            override fun areItemsTheSame(oldItem: ChannelUiItem, newItem: ChannelUiItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ChannelUiItem,
                newItem: ChannelUiItem
            ): Boolean {
                return oldItem == newItem //&& (oldItem is ChannelUiItem.Channel && newItem is ChannelUiItem.Channel && oldItem.isSelected == newItem.isSelected)
            }
        }
    }
}