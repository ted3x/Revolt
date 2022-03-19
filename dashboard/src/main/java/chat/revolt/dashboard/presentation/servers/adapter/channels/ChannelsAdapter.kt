/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 4:26 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 4:26 PM
 */

package chat.revolt.dashboard.presentation.servers.adapter.channels

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import chat.revolt.domain.models.channel.Channel

class ChannelsAdapter : ListAdapter<Channel, BaseChannelViewHolder<*, in Channel>>(COMPARATOR) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseChannelViewHolder<*, in Channel> {
        return when (viewType) {
            TEXT -> TextChannelViewHolder(parent) as BaseChannelViewHolder<*, in Channel>
            else -> VoiceChannelViewHolder(parent) as BaseChannelViewHolder<*, in Channel>
        }
    }

    override fun onBindViewHolder(holder: BaseChannelViewHolder<*, in Channel>, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item is Channel.VoiceChannel) VOICE else TEXT
    }

    companion object {

        private const val TEXT = 4
        private const val VOICE = 5
        private val COMPARATOR = object : DiffUtil.ItemCallback<Channel>() {
            override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean {
                return oldItem == newItem
            }
        }
    }
}