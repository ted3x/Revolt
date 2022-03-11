/*
 * Created by Tedo Manvelidze(ted3x) on 3/11/22, 11:43 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/11/22, 11:43 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.adapter.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import chat.revolt.dashboard.databinding.GroupedTextAdapterItemBinding
import chat.revolt.domain.models.Message

class GroupedTextMessageViewHolder(val parent: ViewGroup) :
    MessageViewHolder<GroupedTextAdapterItemBinding>(
        GroupedTextAdapterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) {

    override fun onBind(item: Message) {
        binding.text.text = (item.content as? Message.Content.Message)?.content
            ?: (item.content as? Message.Content.Text)?.content
    }
}