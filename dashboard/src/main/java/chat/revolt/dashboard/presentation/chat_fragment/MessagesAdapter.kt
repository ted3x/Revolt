/*
 * Created by Tedo Manvelidze(ted3x) on 2/26/22, 11:41 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/26/22, 11:41 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import chat.revolt.core.UlidTimeDecoder
import chat.revolt.core.extensions.toDate
import chat.revolt.core.extensions.toHour
import chat.revolt.dashboard.databinding.SystemTextAdapterItemBinding
import chat.revolt.dashboard.databinding.TextAdapterItemBinding
import chat.revolt.domain.models.Message
import chat.revolt.styles.R
import com.bumptech.glide.Glide
import java.lang.IllegalStateException

class MessagesAdapter : PagingDataAdapter<Message, MessagesAdapter.MessageViewHolder>(Comparator) {

    abstract class MessageViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun onBind(item: Message)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return when (viewType) {
            MESSAGE -> TextMessageViewHolder(
                TextAdapterItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            SYSTEM_MESSAGE -> SystemTextMessageViewHolder(
                SystemTextAdapterItemBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            else -> throw IllegalStateException("")
        }
    }

    class TextMessageViewHolder(private val binding: TextAdapterItemBinding) : MessageViewHolder(binding) {

        override fun onBind(item: Message) {
            binding.authorName.text = item.authorName
            binding.text.text = (item.content as? Message.Content.Message)?.content
                ?: (item.content as? Message.Content.Text)?.content
            binding.date.text = UlidTimeDecoder.getTimestamp(item.id).toDate(binding.root.context)
            Glide.with(binding.root.context).load(item.author.avatarUrl).into(binding.authorImage)
        }
    }

    class SystemTextMessageViewHolder(private val binding: SystemTextAdapterItemBinding) : MessageViewHolder(binding) {

        fun Message.Content.getSystemMessageIconRes(): Int {
            return when (this) {
                is Message.Content.ChannelDescriptionChanged, is Message.Content.ChannelIconChanged, is Message.Content.ChannelRenamed -> R.drawable.ic_channel_edit
                is Message.Content.UserAdded -> R.drawable.ic_user_add
                is Message.Content.UserBanned -> R.drawable.ic_user_blocked
                is Message.Content.UserJoined -> R.drawable.ic_user_joined
                is Message.Content.UserKicked -> R.drawable.ic_user_remove
                is Message.Content.UserLeft -> R.drawable.ic_user_left
                is Message.Content.UserRemove -> R.drawable.ic_user_remove
                else -> -1
            }
        }

        override fun onBind(item: Message) {
            binding.root.setOnClickListener {
                binding.messageDate.visibility =
                    if (binding.messageDate.visibility == View.VISIBLE) View.INVISIBLE
                    else View.VISIBLE
            }
            val iconRes = item.content.getSystemMessageIconRes()
            binding.systemMessageIcon.setImageDrawable(ContextCompat.getDrawable(binding.root.context, iconRes))
            binding.message.text = (item.content as? Message.SystemMessage)?.message
            binding.messageDate.text = UlidTimeDecoder.getTimestamp(item.id).toHour()
            Glide.with(binding.root.context).load((item.content as? Message.SystemMessage)?.authorImageUrl)
                .into(binding.authorImage)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item?.content is Message.Content.Text || item?.content is Message.Content.Message) MESSAGE
        else if (item?.content is Message.SystemMessage) SYSTEM_MESSAGE
        else -1
    }

    companion object {
        object Comparator : DiffUtil.ItemCallback<Message>() {
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }
        }

        const val MESSAGE = 1234123
        const val SYSTEM_MESSAGE = 12312414
    }

}