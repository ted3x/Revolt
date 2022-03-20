///*
// * Created by Tedo Manvelidze(ted3x) on 3/11/22, 11:44 PM
// * Copyright (c) 2022 . All rights reserved.
// * Last modified 3/11/22, 11:44 PM
// */
//
//package chat.revolt.dashboard.presentation.chat_fragment.adapter.view_holders
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.core.content.ContextCompat
//import chat.revolt.core.extensions.toHour
//import chat.revolt.dashboard.databinding.SystemTextAdapterItemBinding
//import chat.revolt.domain.UlidTimeDecoder
//import chat.revolt.domain.models.Message
//import chat.revolt.styles.R
//import com.bumptech.glide.Glide
//
//class SystemTextMessageViewHolder(parent: ViewGroup) : MessageViewHolder<SystemTextAdapterItemBinding>(
//        SystemTextAdapterItemBinding.inflate(
//            LayoutInflater.from(
//                parent.context
//            ), parent, false
//        )
//    ) {
//
//    override fun onBind(item: Message) {
//        binding.root.setOnClickListener {
//            binding.messageDate.visibility =
//                if (binding.messageDate.visibility == View.VISIBLE) View.INVISIBLE
//                else View.VISIBLE
//        }
//        val iconRes = item.content.getSystemMessageIconRes()
//        binding.systemMessageIcon.setImageDrawable(
//            ContextCompat.getDrawable(
//                binding.root.context,
//                iconRes
//            )
//        )
//        binding.message.text = (item.content as? Message.SystemMessage)?.message
//        binding.messageDate.text = UlidTimeDecoder.getTimestamp(item.id).toHour()
//        Glide.with(binding.root.context)
//            .load((item.content as? Message.SystemMessage)?.authorImageUrl)
//            .into(binding.authorImage)
//    }
//
//    private fun Message.Content.getSystemMessageIconRes(): Int {
//        return when (this) {
//            is Message.Content.ChannelDescriptionChanged, is Message.Content.ChannelIconChanged, is Message.Content.ChannelRenamed -> R.drawable.ic_channel_edit
//            is Message.Content.UserAdded -> R.drawable.ic_user_add
//            is Message.Content.UserBanned -> R.drawable.ic_user_blocked
//            is Message.Content.UserJoined -> R.drawable.ic_user_joined
//            is Message.Content.UserKicked -> R.drawable.ic_user_remove
//            is Message.Content.UserLeft -> R.drawable.ic_user_left
//            is Message.Content.UserRemove -> R.drawable.ic_user_remove
//            else -> -1
//        }
//    }
//}
