/*
 * Created by Tedo Manvelidze(ted3x) on 3/11/22, 11:43 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 3/11/22, 11:38 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import chat.revolt.core.paging_manager.LoadingAdapter
import chat.revolt.core.paging_manager.LoadingAdapterListener
import chat.revolt.dashboard.presentation.chat_fragment.adapter.view_holders.GroupedTextMessageViewHolder
import chat.revolt.dashboard.presentation.chat_fragment.adapter.view_holders.MessageViewHolder
import chat.revolt.dashboard.presentation.chat_fragment.adapter.view_holders.TextMessageViewHolder

class MessagesAdapter(listener: LoadingAdapterListener) :
    LoadingAdapter<MessageUiModel, LoadingAdapter.BaseLoadingAdapterViewHolder>(
        Comparator,
        listener
    ) {

    override val emptyItem: MessageUiModel
        get() = MessageUiModel.EMPTY

    override val loadOffset: Int = 10

    override fun getViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<*> {
        return when (viewType) {
            MESSAGE -> TextMessageViewHolder(parent)
            GROUPED_MESSAGE -> GroupedTextMessageViewHolder(parent)
            //SYSTEM_MESSAGE -> SystemTextMessageViewHolder(parent)
            else -> throw IllegalStateException("")
        }
    }

    override fun onBindViewHolder(holder: BaseLoadingAdapterViewHolder, position: Int) {
        val item = getItem(position)
        if (holder is MessageViewHolder<*>) holder.onBind(item)
    }

    override fun getViewType(position: Int): Int {
        val item = getItem(position)
        return if (position == itemCount - 1) MESSAGE else {
            val previousItem = getItem(position + 1)
            if (previousItem == null || item.isDivided(previousItem)) MESSAGE else GROUPED_MESSAGE
        }
    }

    companion object {
        object Comparator : DiffUtil.ItemCallback<MessageUiModel>() {
            override fun areItemsTheSame(
                oldItem: MessageUiModel,
                newItem: MessageUiModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MessageUiModel,
                newItem: MessageUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }

        const val MESSAGE = 111
        const val GROUPED_MESSAGE = 112
        const val SYSTEM_MESSAGE = 113
    }
}