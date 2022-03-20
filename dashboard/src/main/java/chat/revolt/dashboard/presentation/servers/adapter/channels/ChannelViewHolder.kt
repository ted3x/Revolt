/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 4:28 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 4:28 PM
 */

package chat.revolt.dashboard.presentation.servers.adapter.channels

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import chat.revolt.core.extensions.load
import chat.revolt.core.extensions.visibleIf
import chat.revolt.dashboard.R
import chat.revolt.dashboard.databinding.ChannelItemBinding
import chat.revolt.dashboard.presentation.servers.adapter.ChannelUiItem

class ChannelViewHolder(parent: ViewGroup, onChannelClick: (channelId: String) -> Unit, ) : BaseChannelViewHolder<ChannelItemBinding, ChannelUiItem.Channel>(
    ChannelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    private var channel: ChannelUiItem.Channel? = null

    init {
        binding.root.setOnClickListener { channel?.id?.let { id -> onChannelClick.invoke(id) } }
    }

    override fun onBind(item: ChannelUiItem.Channel) {
        channel = item
        if(!item.isVisible) {
            itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        }
        else {
            itemView.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, context.resources.getDimensionPixelSize(R.dimen.dimen_p_50))
        }
        itemView.visibleIf { item.isVisible }
        if(item.isSelected) {
            binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.tertiary_foreground))
        }
        else {
            binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.background))
        }
        if(item.iconUrl != null) {
            binding.channelIcon.load(item.iconUrl)
        }
        else {
            binding.channelIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_text_channel))
        }
        binding.channelName.text = item.name
    }

}