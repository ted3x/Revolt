/*
 * Created by Tedo Manvelidze(ted3x) on 3/11/22, 11:43 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/11/22, 11:43 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.adapter.view_holders

import android.view.LayoutInflater
import android.view.ViewGroup
import chat.revolt.core.extensions.toDate
import chat.revolt.dashboard.databinding.TextAdapterItemBinding
import chat.revolt.dashboard.presentation.chat_fragment.adapter.MessageUiModel
import com.bumptech.glide.Glide

class TextMessageViewHolder(val parent: ViewGroup) : MessageViewHolder<TextAdapterItemBinding>(
    TextAdapterItemBinding.inflate(
        LayoutInflater.from(
            parent.context
        ), parent, false
    )
) {

    override fun onBind(item: MessageUiModel) {
        binding.authorName.text = item.authorName
        binding.date.text = item.timestamp.toDate(binding.root.context)
        Glide.with(binding.root.context).load(item.authorAvatarUrl).into(binding.authorImage)
        binding.text.text = item.content
    }
}