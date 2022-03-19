/*
 * Created by Tedo Manvelidze(ted3x) on 3/19/22, 4:28 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/19/22, 4:28 PM
 */

package chat.revolt.dashboard.presentation.servers.adapter.channels

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import chat.revolt.core.extensions.load
import chat.revolt.dashboard.R
import chat.revolt.dashboard.databinding.ChannelItemBinding
import chat.revolt.domain.models.channel.Channel

class TextChannelViewHolder(parent: ViewGroup) : BaseChannelViewHolder<ChannelItemBinding, Channel.TextChannel>(
    ChannelItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun onBind(channel: Channel.TextChannel) {
        if(channel.icon?.url != null) {
            binding.channelIcon.load(channel.icon!!.url)
        }
        else {
            binding.channelIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_text_channel))
        }
        binding.channelName.text = channel.name
    }

}