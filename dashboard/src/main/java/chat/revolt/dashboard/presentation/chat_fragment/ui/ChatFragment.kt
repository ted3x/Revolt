/*
 * Created by Tedo Manvelidze(ted3x) on 2/13/22, 9:24 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 2/13/22, 9:24 PM
 */

package chat.revolt.dashboard.presentation.chat_fragment.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.*
import androidx.paging.Pager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import chat.revolt.core.PagedListDelegationAdapter
import chat.revolt.core.fragment.BaseFragment
import chat.revolt.dashboard.R
import chat.revolt.dashboard.databinding.ChatFragmentBinding
import chat.revolt.dashboard.databinding.ImageAdapterItemBinding
import chat.revolt.dashboard.databinding.TextAdapterItemBinding
import chat.revolt.dashboard.databinding.VideoAdapterItemBinding
import chat.revolt.dashboard.domain.repository.ChannelRepository
import chat.revolt.dashboard.presentation.chat_fragment.di.chatModule
import chat.revolt.dashboard.presentation.chat_fragment.mediator.ChatMediator
import chat.revolt.dashboard.presentation.chat_fragment.vm.ChatViewModel
import chat.revolt.data.local.dao.MessageDao
import chat.revolt.data.local.entity.message.MessageEntity
import chat.revolt.data.local.mappers.MessageDBMapper
import chat.revolt.domain.models.ChatItem
import chat.revolt.domain.models.User
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module.Module
import org.koin.java.KoinJavaComponent.get

class ChatFragment : BaseFragment<ChatViewModel, ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    override val viewModel: ChatViewModel by viewModel()
    override val module: List<Module>
        get() = listOf(chatModule)
    object Comparator: DiffUtil.ItemCallback<MessageEntity>() {
        override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
            return oldItem == newItem
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PagedListDelegationAdapter(Comparator,
            textAdapterItem {

            },
        )
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.chatRecyclerView.scrollToPosition(itemCount -1)
            }
        })
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.chatRecyclerView.adapter = adapter
        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.flow.collectLatest {
                    adapter.submitData(it)
                }
            }
        }
    }


    fun textAdapterItem(itemClickedListener: (MessageEntity) -> Unit) =
        adapterDelegateViewBinding<MessageEntity, MessageEntity, TextAdapterItemBinding>(
            { layoutInflater, root -> TextAdapterItemBinding.inflate(layoutInflater, root, false) }
        ) {
            bind {
                binding.authorName.text = item.author
                binding.text.text = item.content
            }
        }

//    fun textAdapterItem(itemClickedListener: (ChatItem.Text) -> Unit) =
//        adapterDelegateViewBinding<ChatItem.Text, ChatItem, TextAdapterItemBinding>(
//            { layoutInflater, root -> TextAdapterItemBinding.inflate(layoutInflater, root, false) }
//        ) {
//            bind {
//                binding.authorName.text = item.author.username
//                binding.text.text = item.text
//                Glide.with(context).load(item.author.avatarUrl).into(binding.authorImage)
//            }
//        }
//
//    fun imageAdapterItem(itemClickedListener: (ChatItem.Image) -> Unit) =
//        adapterDelegateViewBinding<ChatItem.Image, ChatItem, ImageAdapterItemBinding>(
//            { layoutInflater, root -> ImageAdapterItemBinding.inflate(layoutInflater, root, false) }
//        ) {
//            bind {
//                binding.authorName.text = item.author.username
//                Glide.with(context).load(item.author.avatarUrl).into(binding.authorImage)
//                Glide.with(context).load(item.imageUrl).centerInside().into(binding.image)
//            }
//        }
//
//    fun videoAdapterItem(itemClickedListener: (ChatItem.Video, Player) -> Unit) =
//        adapterDelegateViewBinding<ChatItem.Video, ChatItem, VideoAdapterItemBinding>(
//            { layoutInflater, root -> VideoAdapterItemBinding.inflate(layoutInflater, root, false) }
//        ) {
//            binding.root.setOnClickListener {
//                binding.video.player?.let { it1 -> itemClickedListener.invoke(item, it1) }
//            }
//            bind {
//                val player = ExoPlayer.Builder(context).build()
//                val mediaItem = MediaItem.fromUri(item.videoUrl)
//                player.addMediaItem(mediaItem)
//                Glide.with(context).load(item.author.avatarUrl).into(binding.authorImage)
//                binding.authorName.text = item.author.username
//                binding.video.player = player
//            }
//        }
}